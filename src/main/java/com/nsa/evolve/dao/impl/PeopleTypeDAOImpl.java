package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.PeopleTypeDAO;
import com.nsa.evolve.dto.PeopleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by c1633899 on 28/11/2017.
 */
@Repository
public class PeopleTypeDAOImpl implements PeopleTypeDAO{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleTypeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PeopleType> findAllPeopleType() {
        return jdbcTemplate.query("SELECT * FROM people_type",
                new Object[]{},
                (rs, rowNum) -> new PeopleType(
                        rs.getInt("id"),
                        rs.getString("type")
                ));
    }
}
