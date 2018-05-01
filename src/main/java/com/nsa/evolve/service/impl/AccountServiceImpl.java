package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.AccountDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Roles;
import com.nsa.evolve.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    private static Logger infoLog = LoggerFactory.getLogger("InfoLog");

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
        String hashedNewPassword = passwordEncoder.encode(newPassword);

        if (passwordEncoder.matches(currentPassword, account.getPassword())){
            accountDAO.changePassword(hashedNewPassword, accountId);
            infoLog.info("Account with ID successfully changed their password");
            return true;
        }

        infoLog.info("Account with ID failed to change their password");
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
