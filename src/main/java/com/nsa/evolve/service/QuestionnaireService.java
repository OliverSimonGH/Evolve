package com.nsa.evolve.service;


import com.nsa.evolve.dto.Questionnaire;

import java.util.List;

public interface QuestionnaireService {

    List<Questionnaire> findAllQuestionnaires();
}
