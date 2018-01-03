package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.ScoreDAO;
import com.nsa.evolve.dto.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by c1633899 on 20/11/2017.
 */
@Repository
public class ScoreDAOImpl implements ScoreDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ScoreDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertScoreForComment(Integer score, String comment, Integer fkQuestion, Integer fkModule, Integer fk_result) {
        jdbcTemplate.update("INSERT INTO scores (score, comment, fk_question, fk_module, fk_result) VALUES (?, ?, ?, ?, ?)", score, comment, fkQuestion, fkModule, fk_result);

    }

    @Override
    public List<Score> findAllByModule(Integer module, Integer question, Integer result) {
        return jdbcTemplate.query("SELECT s.id, s.score, s.comment, s.fk_question, s.fk_module, s.fk_result FROM assessment a JOIN result r ON a.id = r.fk_assessment JOIN scores s ON r.fk_assessment = s.fk_result WHERE s.fk_question = ? AND s.fk_module = ? AND fk_result = ?",
                new Object[]{question, module, result},
                (rs, rowNum) -> new Score(
                        rs.getInt("s.id"),
                        rs.getInt("s.score"),
                        rs.getString("s.comment"),
                        rs.getInt("s.fk_question"),
                        rs.getInt("s.fk_module"),
                        rs.getInt("s.fk_result")
                ));
    }

    @Override
    public void updateScoresForModule(Integer result, Integer module) {
        jdbcTemplate.update("UPDATE scores SET fk_result = ? WHERE fk_module = ? AND fk_result IS NULL", result, module);
    }
}
