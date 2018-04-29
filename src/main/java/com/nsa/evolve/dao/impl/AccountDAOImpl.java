package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.AccountDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Repository
public class AccountDAOImpl implements AccountDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Account findAccount(String email) {
        Account account = jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ?",
                new Object[]{email},
                (rs, rowNum) -> new Account(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        null
                ));

        List<String> authorities = getUserRoles(account.getId());
        account.setRoles(authorities);
        return account;
    }

    @Override
    public void createAccount(String email, String password) {
        jdbcTemplate.update("INSERT INTO account (email, password) VALUES (?, ?)", email, password);
    }

    @Override
    public Boolean checkAccountExists(String email) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ?",
                    new Object[]{email},
                    (rs, rowNum) -> new Account(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            null
                    ));

            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public void changePassword(String password, Integer accountId) {
        jdbcTemplate.update("UPDATE account SET password = ? WHERE id = ?", password, accountId);
    }

    @Override
    @Transactional
    public Account findAccountById(Integer accountId) {
        List<String> authorities = getUserRoles(accountId);

        return jdbcTemplate.queryForObject("SELECT * FROM account WHERE id = ?",
                new Object[]{accountId},
                (rs, rowNum) -> new Account(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        authorities
                ));

    }

    @Override
    @Transactional
    public Account findAccountByUsername(String email) {
        try {
            Account account = jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ?",
                    new Object[]{email},
                    (rs, rowNum) -> new Account(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            null
                    ));

            List<String> authorities = getUserRoles(account.getId());
            account.setRoles(authorities);
            return account;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void insertRoles(Integer accountId, Roles role) {
        jdbcTemplate.update("INSERT INTO account_role (fk_account, fk_role) VALUES (?, ?)", accountId, role.getId());
    }

    @Override
    public List<String> getUserRoles(int id) {
        return jdbcTemplate.queryForList("SELECT r.role FROM account a JOIN account_role ar ON a.id = ar.fk_account JOIN role r ON r.id = ar.fk_role WHERE a.id = ?",
                new Object[]{id}, String.class);
    }
}
