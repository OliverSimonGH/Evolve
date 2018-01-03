package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.QuestionnaireDAO;
import com.nsa.evolve.dto.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by c1633899 on 13/12/2017.
 */
@Repository
public class QuestionnaireDAOImpl implements QuestionnaireDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Questionnaire> findAllQuestionnaires() {
        return jdbcTemplate.query("SELECT * FROM questionnaire",
                new Object[]{},
                (rs, rowNum) -> new Questionnaire(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("fk_module")
                ));
    }
}
