package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Question;

import java.util.List;

public interface QuestionDAO {

    List<Question> findAllQuestionsByQuestionnaire(Integer foreignKey);
    void editQuestion(Integer id, String question);
    void deleteQuestion(Integer id);
    void createQuestion(String question, Integer questionnaire);
}
