package com.nsa.evolve.service;

import com.nsa.evolve.dto.Result;

import java.util.List;

public interface ResultService {

    Integer findTotalQuestionnaire(Integer fk_module, Integer fk_company);
    int getQviScore(Integer companyID);
    void insertResults(Integer module, Integer company);
    List<Result> findAllByAssessment(Integer id);
}
