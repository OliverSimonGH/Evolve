package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.AssessmentDAO;
import com.nsa.evolve.dto.Assessment;
import com.nsa.evolve.dto.Module;
import com.nsa.evolve.dto.RMAJoin;
import com.nsa.evolve.dto.Result;
import com.nsa.evolve.service.AssessmentService;
import com.nsa.evolve.service.ModuleService;
import com.nsa.evolve.service.ResultService;
import com.nsa.evolve.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by c1645601 on 12/12/2017.
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {

    private AssessmentDAO assessmentDAO;
    private ResultService resultService;
    private ModuleService moduleService;
    private ScoreService scoreService;

    @Autowired
    public AssessmentServiceImpl(AssessmentDAO assessmentDAO, ResultService resultService, ModuleService moduleService, ScoreService scoreService) {
        this.assessmentDAO = assessmentDAO;
        this.resultService = resultService;
        this.moduleService = moduleService;
        this.scoreService = scoreService;
    }

    @Override
    public void createAssessment(Integer companyID) {
        List<Module> moduleList = moduleService.findAllModulesByCompany(companyID);
        moduleList.forEach(module -> System.out.println(module.getId()));

        assessmentDAO.createAssessment(companyID);

        moduleList.forEach(module -> resultService.insertResults(module.getId(), companyID));

        Assessment assessment = assessmentDAO.getAssessmentLastCreated(companyID);

        List<Result> results = resultService.findAllByAssessment(assessment.getId());
        results.forEach(result -> result.getFkAssessment());

        results.forEach(result -> scoreService.updateScoresForModule(result.getFkAssessment(), result.getFkModule()));

        int qvi = resultService.getQviScore(companyID);

        assessmentDAO.addQviToAssessment(qvi,companyID);


    }

    @Override
    public Integer countAssessments(Integer company, Integer module) {
        return assessmentDAO.countAssessments(company, module);
    }

    @Override
    public void approveAssessment(Integer assessment) {
        assessmentDAO.approveAssessment(assessment);
    }

    @Override
    public List<Assessment> getAllByCompanyId(Integer id) {
        return assessmentDAO.getAllByCompanyId(id);
    }

    @Override
    public List<RMAJoin> getRMADataByAssessment(Integer id) {
        return assessmentDAO.getRMADataByAssessment(id);
    }


}
