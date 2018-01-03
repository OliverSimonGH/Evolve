package com.nsa.evolve.service;


import com.nsa.evolve.dto.Assessment;
import com.nsa.evolve.dto.RMAJoin;

import java.util.List;

/**
 * Created by c1645601 on 12/12/2017.
 */

public interface AssessmentService {
    void createAssessment(Integer companyID);
    Integer countAssessments(Integer company, Integer module);
    void approveAssessment(Integer assessment);
    List<Assessment> getAllByCompanyId(Integer id);
    List<RMAJoin> getRMADataByAssessment(Integer id);
}
