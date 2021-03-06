package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Roles;

import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
public interface AccountDAO {

    Account findAccount(String email);
    void createAccount(String email, String password);
    Boolean checkAccountExists(String email);
    void changePassword(String password, Integer accountId);
    Account findAccountById(Integer accountId);
    Account findAccountByUsername(String email);
    void insertRoles(Integer accountId, Roles roles);
    List<String> getUserRoles(int id);

}
