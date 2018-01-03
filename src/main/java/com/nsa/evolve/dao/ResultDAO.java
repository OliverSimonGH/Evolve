package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Result;

import java.util.List;

public interface ResultDAO {

    Integer findTotalQuestionnaires(Integer fk_module, Integer fk_company);
    Boolean insertScoreIntoResult(Integer assessment, Integer module, Integer score, Integer company);
    int getQviScore(Integer companyID);
    List<Result> findAllByAssessment(Integer id);
}
