package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.AssessmentDAO;
import com.nsa.evolve.dao.ResultDAO;
import com.nsa.evolve.dto.Result;
import com.nsa.evolve.service.ModuleService;
import com.nsa.evolve.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private ResultDAO resultDAO;
    private ModuleService moduleService;
    private AssessmentDAO assessmentDAO;

    @Override
    public Integer findTotalQuestionnaire(Integer fk_module, Integer fk_company) {
        return resultDAO.findTotalQuestionnaires(fk_module, fk_company);
    }

    @Autowired
    public ResultServiceImpl(ResultDAO resultDAO, ModuleService moduleService, AssessmentDAO assessmentDAO) {
        this.resultDAO = resultDAO;
        this.moduleService = moduleService;
        this.assessmentDAO = assessmentDAO;
    }

    @Override
    public void insertResults(Integer module, Integer company) {
        int assessment = assessmentDAO.getAssessmentId(company);
        int score = moduleService.getModuleScore(module);
        System.out.println(assessment+" assessment id " + score);


        if (score != 0){
            resultDAO.insertScoreIntoResult(assessment,module,score,company);
        }
    }

    @Override
    public List<Result> findAllByAssessment(Integer id) {
        return resultDAO.findAllByAssessment(id);
    }

    @Override
    public int getQviScore(Integer companyID) {
        return resultDAO.getQviScore(companyID);
    }

}
