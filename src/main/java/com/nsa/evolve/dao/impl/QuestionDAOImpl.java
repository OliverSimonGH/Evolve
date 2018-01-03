package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.QuestionDAO;
import com.nsa.evolve.dto.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Repository
public class QuestionDAOImpl implements QuestionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Question> findAllQuestionsByQuestionnaire(Integer foreignKey) {
        return jdbcTemplate.query("SELECT * FROM questions WHERE fk_questionnaire = ?",
                new Object[]{foreignKey},
                (rs, rowNum) -> new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getInt("fk_questionnaire")
                ));
    }

    @Override
    public void editQuestion(Integer id, String question) {
        jdbcTemplate.update("UPDATE questions SET question = ? WHERE id = ?", question, id);
    }

    @Override
    public void deleteQuestion(Integer id) {
        jdbcTemplate.update("DELETE FROM questions WHERE id = ?", id);
    }

    @Override
    public void createQuestion(String question, Integer questionnaire) {
        jdbcTemplate.update("INSERT INTO questions (question, fk_questionnaire) VALUES(?, ?)", question, questionnaire);
    }
}
