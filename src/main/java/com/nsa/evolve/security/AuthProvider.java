package com.nsa.evolve.security;

import com.nsa.evolve.dto.AccountDetails;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.impl.AccountDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    private static final Logger infoLogger = LoggerFactory.getLogger("InfoLog");
    private static final Logger warnLogger = LoggerFactory.getLogger("WarnLog");

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        AccountDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername(username);

        System.out.println(accountDetails);
//        If the account exists
        if (accountDetails != null){

//            If password is not hashed
            if (!(accountDetails.getPassword().length() == 60 && accountDetails.getPassword().contains("$2a$10$"))){

//                If password entered equals unhashed password in database
                if (password.equalsIgnoreCase(accountDetails.getPassword())){
                    String encodedPassword = passwordEncoder.encode(password);

                    //Update unhashed password to hashed password
                    accountService.updatePassword(accountDetails.getId(), encodedPassword);
                    infoLogger.info("Account with ID {} password updated from legacy to BCrypt", accountDetails.getId());

                    //Authenticate the user
                    return new UsernamePasswordAuthenticationToken(accountDetails, accountDetails.getPassword(), accountDetails.getAuthorities());
                } else {
//                    If password entered does not equal unhashed password in database
                    warnLogger.warn("Account with ID {} failed authentication", accountDetails.getId());
                    throw new BadCredentialsException("Bad credentials");
                }
            }
//            If password is hashed in the database, check it matches password entered
            else if (BCrypt.checkpw(password, accountDetails.getPassword())){
                infoLogger.info("Account with ID {} successfully authenticated", accountDetails.getId());

                //Authenticate the user
                return new UsernamePasswordAuthenticationToken(accountDetails, accountDetails.getPassword(), accountDetails.getAuthorities());
            } else {
//                If password does not match hashed password in database
                warnLogger.warn("Account with ID {} failed authentication", accountDetails.getId());
                throw new BadCredentialsException("Authentication failed for " + username);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
