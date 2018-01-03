package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Company;

import java.util.List;

public interface CompanyDAO {

    Company findCompanyByAccount(Integer foreignKey);
    void createCompanyAccount(String name, Integer fkAccount);
    List<Company> findCompanyByAssessorId(Integer fkAssessor);
    Company findCompanyNameById(Integer Id);
    List<Integer> getModuleReturnData(Long ModId, Long CompanyId);
    List<Integer> getQVIScoresForCompany(Long id);
    Boolean checkModuleTaken(Long ModId, Long CompanyId);
    Long getCompanyQvi(Long id);
    Long getCompanyQvi(String name);
    Long getIndustryAverage();
}
