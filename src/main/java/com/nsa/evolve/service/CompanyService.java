package com.nsa.evolve.service;

import com.nsa.evolve.dto.ModuleReturnData;
import com.nsa.evolve.dto.Company;

import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
public interface CompanyService {

    Company findCompanyByAccount(String email, String password);
    boolean createCompanyAccount(String name, String email, String password);
    List<Company> findCompanyByAssessorId(Integer fkAssessor);
    Company findCompanyNameById(Integer id);
    List<ModuleReturnData> findModuleScores(int id);
    List<Integer> findQviScores(int id);
    Company findCompanyName(Integer id);
    Long getCompanyQvi(Long id);
    Long getCompanyQvi(String name);
    Long getIndustryAverage();
}
