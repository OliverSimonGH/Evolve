package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.CompanyDAO;
import com.nsa.evolve.dto.Company;
import com.nsa.evolve.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Company findCompanyByAccount(Integer foreignKey) {
        return jdbcTemplate.queryForObject("SELECT * FROM company WHERE fk_account = ?",
                new Object[]{foreignKey},
                (rs, rowNum) -> new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("fk_account")
                ));
    }

    @Override
    public void createCompanyAccount(String name, Integer fkAccount) {
        jdbcTemplate.update("INSERT INTO company (name, fk_assessor, fk_account) VALUES (?, 1, ?)", name, fkAccount);
    }

    @Override
    public List<Company> findCompanyByAssessorId(Integer fkAssessor) {
        return jdbcTemplate.query("SELECT * FROM company WHERE fk_assessor = ?",
                new Object[]{fkAssessor},
                (rs, rowNum) -> new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("fk_assessor"),
                        rs.getInt("fk_account")
                ));
    }

    @Override
    public Company findCompanyNameById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM company WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("fk_assessor"),
                        rs.getInt("fk_account")
                ));
    }

    private boolean checkCompanyExists(String email) {
        try {
            Company company = jdbcTemplate.queryForObject("SELECT * FROM company WHERE email = ?",
                    new Object[]{email},
                    (rs, rowNum) -> new Company(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("fk_account")
                    ));

            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }

    }


    public List<Integer> getQVIScoresForCompany(Long id) {
        try {
            List<Integer> QviList = new ArrayList<>();
            jdbcTemplate.query("SELECT qvi_score FROM assessment WHERE fk_company = ? order by id DESC LIMIT 5",
                    new Object[]{id},
                    (rs, rowNum) -> QviList.add(rs.getInt("qvi_score")));

            return QviList;
        } catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public Boolean checkModuleTaken(Long ModId, Long CompanyId) {

        try {
            Boolean modTaken = Boolean.FALSE;
            List<Long> countList = new ArrayList<>();
            jdbcTemplate.query("SELECT fk_module FROM result WHERE fk_module = ? AND fk_company = ?",
                    new Object[]{ModId,CompanyId},
                    (rs, rowNum) ->  countList.add((long) rs.getInt("fk_module"))
            );

            if (countList.size() ==0){

                modTaken = Boolean.FALSE;
            }
            else{

                modTaken = Boolean.TRUE;
            }
            return modTaken;

        } catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public List<Integer> getModuleReturnData(Long ModId, Long CompanyId) {
        try {
            System.out.println("Getting module data");
            List<Integer> ModuleScoreList = new ArrayList<>();
            jdbcTemplate.query("SELECT score FROM result WHERE fk_module = ? AND fk_company = ? order by fk_assessment DESC LIMIT 5",
                    new Object[]{ModId,CompanyId},
                    (rs, rowNum) -> ModuleScoreList.add(rs.getInt("score"))
            );
            int size = ModuleScoreList.size();
            while (ModuleScoreList.size() <= 5){
                ModuleScoreList.add(0);
            }
            return ModuleScoreList;

        } catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public Long getCompanyQvi(Long id){
        Long companyQvi;
        try {
            companyQvi = jdbcTemplate.queryForObject("SELECT qvi_score FROM assessment WHERE fk_company = ?  ORDER BY date DESC LIMIT 1;",
                    new Object[]{id},Long.class);


            return companyQvi;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Long getCompanyQvi(String name){
        Long companyQvi;
        try {
            companyQvi = jdbcTemplate.queryForObject("SELECT qvi_score FROM assessment INNER JOIN company ON Assessment.fk_company = company.id WHERE company.id = (SELECT id FROM company WHERE name = ?)  ORDER BY date DESC LIMIT 1;",
                    new Object[]{name},Long.class);


            return companyQvi;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Long getIndustryAverage(){
        Long industryAverage;
        try {
            industryAverage = jdbcTemplate.queryForObject("SELECT AVG(qvi_score) FROM assessment;",
                    new Object[]{},Long.class);

            return industryAverage;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }


}
