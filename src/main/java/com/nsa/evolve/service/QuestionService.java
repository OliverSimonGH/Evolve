package com.nsa.evolve.service;

import com.nsa.evolve.dto.Question;

import java.util.List;

public interface QuestionService {

    List<Question> findAllQuestionsByQuestionnaire(Integer fkQuestionnaire);
    void editQuestion(Integer id, String question);
    void deleteQuestion(Integer id);
    void createQuestion(String question, Integer questionnaire);
}
