package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.AccountDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.AccountDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;

    private static final Logger warnLogger = LoggerFactory.getLogger("WarnLog");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountDAO.findAccountByUsername(username);
        if (account == null) {
            warnLogger.warn("Account with username {} was not found when authenticating", username);
            throw new UsernameNotFoundException("Username Not Found");
        }
        else return new AccountDetails(account);
    }
}
