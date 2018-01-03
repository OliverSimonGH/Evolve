package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.PeopleDAO;
import com.nsa.evolve.dto.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Repository
public class PeopleDAOImpl implements PeopleDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public People findPeopleByAccount(Integer foreignKey) {
        return jdbcTemplate.queryForObject("SELECT * FROM people WHERE fk_account = ?",
                new Object[]{foreignKey},
                (rs, rowNum) -> new People(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("fk_company"),
                        rs.getInt("fk_account"),
                        rs.getInt("fk_type")
                ));
    }

    @Override
    public void createPeopleAccount(String firstName, String lastName, Integer fkCompany, Integer fkAccount, Integer fkType) {
        jdbcTemplate.update("INSERT INTO people (first_name, last_name, fk_company, fk_account, fk_type) VALUES (?, ?, ?, ?, ?)", firstName, lastName, fkCompany, fkAccount, fkType);
    }
}
