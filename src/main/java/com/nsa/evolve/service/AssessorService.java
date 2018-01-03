package com.nsa.evolve.service;

import com.nsa.evolve.classes.ShortCompanyData;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Assessor;

import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
public interface AssessorService {

    Assessor findAssessorByAccount(String email, String password);
    Boolean createAssessorAccount(String first_name, String email, String password);
    List<ShortCompanyData> getAssessorCompanies(Long id);
    String getAssessorName(Long id);

}
