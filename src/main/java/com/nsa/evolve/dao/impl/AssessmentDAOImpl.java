package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.AssessmentDAO;
import com.nsa.evolve.dto.Assessment;
import com.nsa.evolve.dto.RMAJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by c1645601 on 12/12/2017.
 */
@Repository
public class AssessmentDAOImpl implements AssessmentDAO{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AssessmentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createAssessment(Integer company) {
            jdbcTemplate.update("INSERT INTO assessment (date,approved, qvi_score,fk_company) VALUES (DATE(NOW()), 0, 0, ?)", company);
    }


    @Override
    public int getAssessmentId(Integer companyID){
        try {
            return jdbcTemplate.queryForObject("SELECT id from assessment where fk_company = ? Order by id DESC Limit 1 ;",
                    new Object[]{companyID}, Integer.class);

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public void addQviToAssessment(Integer qvi,Integer companyID){
        int assessmentId = getAssessmentId(companyID);
        jdbcTemplate.update("UPDATE assessment SET qvi_score = ? WHERE id = ?",qvi,assessmentId);

    }

    @Override
    public Integer countAssessments(Integer company, Integer module) {
        return jdbcTemplate.queryForObject("SELECT count(*) + 1 from result where fk_company = ? AND fk_module = ?",
                new Object[] {company, module}, Integer.class);
    }

    @Override
    public void approveAssessment(Integer assessment) {
        jdbcTemplate.update("UPDATE assessment SET approved = 1 WHERE id = ?", assessment);
    }

    @Override
    public List<Assessment> getAllByCompanyId(Integer id) {
        return jdbcTemplate.query("SELECT * FROM assessment WHERE fk_company = ? ORDER BY approved",
                new Object[]{id},
                (rs, rowNum) -> new Assessment(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getInt("approved"),
                        rs.getInt("qvi_score"),
                        rs.getInt("fk_company")
                ));
    }

    @Override
    public List<RMAJoin> getRMADataByAssessment(Integer id) {
        return jdbcTemplate.query("SELECT r.fk_module, m.fk_module, mt.name FROM module m JOIN moduletype mt ON m.fk_module = mt.id JOIN result r ON r.fk_module = m.id JOIN assessment a ON r.fk_assessment = a.id WHERE a.id = ?",
                new Object[]{id},
                (rs, rowNum) -> new RMAJoin(
                        rs.getInt("r.fk_module"),
                        rs.getInt("m.fk_module"),
                        rs.getString("mt.name")
                ));
    }

    @Override
    public Assessment getAssessmentLastCreated(Integer companyID){
        return jdbcTemplate.queryForObject("SELECT * FROM assessment WHERE fk_company = ? ORDER BY id DESC LIMIT 1",
                new Object[]{companyID},
                (rs, rowNum) -> new Assessment(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getInt("approved"),
                        rs.getInt("qvi_score"),
                        rs.getInt("fk_company")
                ));
    }


}
