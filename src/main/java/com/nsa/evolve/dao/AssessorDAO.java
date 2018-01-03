package com.nsa.evolve.dao;

import com.nsa.evolve.classes.ShortCompanyData;
import com.nsa.evolve.dto.Assessor;

import java.util.List;

public interface AssessorDAO {

    Assessor findAssessorByAccount(Integer foreignKey);
    void createAssessorAccount(String firstName, Integer foreignKey);
    List<ShortCompanyData> getAssessorCompanies(Long id);
    String getAssessorName(Long id);

}
