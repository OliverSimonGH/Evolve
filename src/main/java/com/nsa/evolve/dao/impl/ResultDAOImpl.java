package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.AssessmentDAO;
import com.nsa.evolve.dao.ResultDAO;
import com.nsa.evolve.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResultDAOImpl implements ResultDAO {

    private JdbcTemplate jdbcTemplate;
    private AssessmentDAO assessmentDAO;

    @Autowired
    public ResultDAOImpl(JdbcTemplate jdbcTemplate,AssessmentDAO assessmentDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.assessmentDAO = assessmentDAO;
    }

    public Integer findTotalQuestionnaires(Integer fk_module, Integer fk_company) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM result WHERE fk_module = ? AND fk_company = ?",
                new Object[]{fk_module, fk_company}, Integer.class);

        return result;
    }

    @Override
    public Boolean insertScoreIntoResult(Integer assessment, Integer module, Integer score, Integer company){
        System.out.println(assessment+ module+ score+company );
        jdbcTemplate.update("INSERT INTO result (fk_assessment, fk_module,score, fk_company) VALUES (?, ?, ?, ?)", assessment, module, score, company);
        return null;
    }

    @Override
    public int getQviScore(Integer company){
        int assId = assessmentDAO.getAssessmentId(company);
        int qviScore;
        try {
           qviScore = jdbcTemplate.queryForObject("SELECT ROUND(AVG(score)) FROM result WHERE fk_company = ? AND score != 0 AND fk_assessment = ?",
                    new Object[]{company,assId},Integer.class);
            return qviScore;
        } catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public List<Result> findAllByAssessment(Integer id) {
        return jdbcTemplate.query("SELECT * FROM result WHERE fk_assessment = ?",
                new Object[]{id},
                (rs, rowNum) -> new Result(
                        rs.getInt("fk_assessment"),
                        rs.getInt("fk_module"),
                        rs.getInt("score"),
                        rs.getInt("fk_company")
                ));
    }
}
