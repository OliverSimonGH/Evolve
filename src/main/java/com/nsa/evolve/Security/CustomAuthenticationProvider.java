//package com.nsa.evolve.Security;
//
//import com.nsa.evolve.dto.AccountDetails;
//import com.nsa.evolve.service.AccountService;
//import com.nsa.evolve.service.impl.AccountDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private AccountDetailsService accountDetailsService;
//
//    @Lazy
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        AccountDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername(username);
//
//        //If account Exists
//        if (accountDetails != null){
//            System.out.println("1");
//            //If password is not hashed
//            if (!(accountDetails.getPassword().length() == 60 && accountDetails.getPassword().contains("$2a$10$"))){
//                System.out.println("2");
//                //If password matches unhashed password in database
//                if (password.equalsIgnoreCase(accountDetails.getPassword())){
//                    System.out.println("3");
//                    String encodedPassword = passwordEncoder.encode(password);
//                    accountService.updatePassword(accountDetails.getId(), encodedPassword);
//                    return new UsernamePasswordAuthenticationToken(accountDetails.getUsername(), accountDetails.getPassword(), accountDetails.getAuthorities());
//                } else throw new BadCredentialsException("Bad credentials");
//
//            }
//
//            else if (BCrypt.checkpw(password, accountDetails.getPassword())){
//                System.out.println("4");
//                return new UsernamePasswordAuthenticationToken(accountDetails.getUsername(), accountDetails.getPassword(), accountDetails.getAuthorities());
//            }
//        } else throw new BadCredentialsException("Authentication failed for " + username);
//
//        System.out.println("5");
//
//        return null;
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
