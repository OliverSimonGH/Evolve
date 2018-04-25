package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.AccountDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Roles;
import com.nsa.evolve.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account findByEmail(String email) {
        try {
            return accountDAO.findAccount(email);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Boolean createAccount(String email, String password) {
        if (!accountDAO.checkAccountExists(email)){
            accountDAO.createAccount(email, passwordEncoder.encode(password));
            return true;
        }
        return false;
    }

    @Override
    public Boolean changePassword(String currentPassword, String newPassword, Integer accountId) {
        Account account = accountDAO.findAccountById(accountId);

        if (account.getPassword().equalsIgnoreCase(currentPassword)){
            accountDAO.changePassword(newPassword, accountId);
            return true;
        }

        return false;
    }

    @Override
    public void insertRoles(Integer accountId, Roles role) {
        accountDAO.insertRoles(accountId, role);
    }

    @Override
    public void updatePassword(Integer accountId, String password) {
        accountDAO.changePassword(password, accountId);
    }
}
