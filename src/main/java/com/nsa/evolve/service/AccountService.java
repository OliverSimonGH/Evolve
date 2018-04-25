package com.nsa.evolve.service;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Roles;

/**
 * Created by c1633899 on 24/11/2017.
 */
public interface AccountService {

    Account findByEmail(String email);
    Boolean createAccount(String email, String password);
    Boolean changePassword(String currentPassword, String newPassword, Integer accountId);
    void insertRoles(Integer accountId, Roles role);
    void updatePassword(Integer accountId, String password);
}
