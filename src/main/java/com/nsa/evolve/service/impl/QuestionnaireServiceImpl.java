package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.QuestionnaireDAO;
import com.nsa.evolve.dto.Questionnaire;
import com.nsa.evolve.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireDAO questionnaireDAO;


    @Override
    public List<Questionnaire> findAllQuestionnaires() {
        return questionnaireDAO.findAllQuestionnaires();
    }
}
