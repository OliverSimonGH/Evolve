package com.nsa.evolve.dao.impl;

import com.nsa.evolve.classes.ShortCompanyData;
import com.nsa.evolve.dao.AssessorDAO;
import com.nsa.evolve.dto.Assessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Repository
public class AssessorDAOImpl implements AssessorDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AssessorDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Assessor findAssessorByAccount(Integer foreignKey) {
        return jdbcTemplate.queryForObject("SELECT * FROM assessor WHERE fk_account = ?",
                new Object[]{foreignKey},
                (rs, rowNum) -> new Assessor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("fk_account")
                ));
    }

    @Override
    public void createAssessorAccount(String firstName, Integer foreignKey) {
        jdbcTemplate.update("INSERT INTO assessor (first_name, fk_account) VALUES (?, ?)", firstName, foreignKey);
    }

    @Override
    public List<ShortCompanyData> getAssessorCompanies(Long id){
        List<ShortCompanyData> company = new ArrayList<>();
        System.out.println("stat of sql");
        try {
            jdbcTemplate.query("SELECT id,name FROM company WHERE fk_assessor = ?",
                    new Object[]{id},
                    (rs, rowNum) -> company.add(new ShortCompanyData(
                            rs.getInt("id"),
                            rs.getString("name")

                    )));
            System.out.println("end of sql");
            return company;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public String getAssessorName(Long id){
        try {
            String assessorName = jdbcTemplate.queryForObject("SELECT name FROM assessor WHERE id = ?",
                    new Object[]{id},
                    String.class);

            return assessorName;
        } catch (EmptyResultDataAccessException e){
            return "No Assessor Found";
        }
    }

}
