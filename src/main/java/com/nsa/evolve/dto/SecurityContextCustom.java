package com.nsa.evolve.dto;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextCustom {

    public static AccountDetails getAccount(){
        AccountDetails account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return account;
    }
}
