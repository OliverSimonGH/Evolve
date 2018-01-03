package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.AccountDAO;
import com.nsa.evolve.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public Account findAccount(String email, String password) {
        return jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ? AND password = ?",
                new Object[]{email, password},
                (rs, rowNum) -> new Account(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("fk_type")
                ));

    }

    @Override
    public void createAccount(String email, String password, Integer foreignKey) {
        jdbcTemplate.update("INSERT INTO account (email, password, fk_type) VALUES (?, ?, ?)", email, password, foreignKey);
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
                            rs.getInt("fk_type")
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
    public Account findAccountById(Integer accountId) {
        return jdbcTemplate.queryForObject("SELECT * FROM account WHERE id = ?",
                new Object[]{accountId},
                (rs, rowNum) -> new Account(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("fk_type")
                ));

    }
}
