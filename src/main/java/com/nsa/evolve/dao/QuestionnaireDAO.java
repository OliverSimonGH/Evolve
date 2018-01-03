package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Questionnaire;

import java.util.List;

/**
 * Created by c1633899 on 13/12/2017.
 */
public interface QuestionnaireDAO {

    List<Questionnaire> findAllQuestionnaires();
}
