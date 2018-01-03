package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.ModuleTypeDAO;
import com.nsa.evolve.dto.ModuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by c1633899 on 30/11/2017.
 */
@Repository
public class ModuleTypeDAOImpl implements ModuleTypeDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ModuleType> findAllModules() {
        return jdbcTemplate.query("SELECT * FROM moduleType",
                new Object[]{},
                (rs, rowNum) -> new ModuleType(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
    }
}
