package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Assessment;
import com.nsa.evolve.dto.RMAJoin;

import java.util.List;

public interface AssessmentDAO {

    Integer countAssessments(Integer company, Integer module);
    void approveAssessment(Integer assessment);
    List<Assessment> getAllByCompanyId(Integer id);
    List<RMAJoin> getRMADataByAssessment(Integer id);
    void createAssessment(Integer company);
    int getAssessmentId(Integer companyID);
    void addQviToAssessment(Integer qvi,Integer companyID);
    Assessment getAssessmentLastCreated(Integer companyID);

}
