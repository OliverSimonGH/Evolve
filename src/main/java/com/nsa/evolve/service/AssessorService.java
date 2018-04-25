package com.nsa.evolve.service;

import com.nsa.evolve.dto.Assessor;
import com.nsa.evolve.dto.ShortCompanyData;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
public interface AssessorService {

    Assessor findAssessorByAccount(String email, String password);
    Boolean createAssessorAccount(String first_name, String email, String password) throws NoSuchAlgorithmException;
    List<ShortCompanyData> getAssessorCompanies(Long id);
    String getAssessorName(Long id);

}
