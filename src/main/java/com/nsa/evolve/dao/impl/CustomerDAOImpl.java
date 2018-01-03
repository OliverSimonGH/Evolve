package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.CustomerDAO;
import com.nsa.evolve.dto.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDAOImpl(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;
    }

    public List<Question> getQuestionnaire() {
        return jdbcTemplate.query("SELECT * FROM questions WHERE fk_questionnaire=1;",
                new Integer[]{},
                (rs, i) -> new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getInt("fk_questionnaire")
                )
        );
    }

}

