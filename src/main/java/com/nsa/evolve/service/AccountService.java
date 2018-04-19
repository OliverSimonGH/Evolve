package com.nsa.evolve.service;

import com.nsa.evolve.dto.Account;

/**
 * Created by c1633899 on 24/11/2017.
 */
public interface AccountService {

    Account findByEmailAndPassword(String email, String password);
    Boolean createAccount(String email, String password, Integer foreignKey);
    String generatePassword(Integer length);
    Boolean changePassword(String currentPassword, String newPassword, Integer accountId);
}
