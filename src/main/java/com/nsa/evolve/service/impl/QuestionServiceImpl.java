package com.nsa.evolve.service.impl;


import com.nsa.evolve.dao.QuestionDAO;
import com.nsa.evolve.dto.Question;
import com.nsa.evolve.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDAO questionDAO;

    @Autowired
    public QuestionServiceImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public List<Question> findAllQuestionsByQuestionnaire(Integer fkQuestionnaire) {
        return questionDAO.findAllQuestionsByQuestionnaire(fkQuestionnaire);
    }

    @Override
    public void editQuestion(Integer id, String question) {
        questionDAO.editQuestion(id, question);
    }

    @Override
    public void deleteQuestion(Integer id) {
        questionDAO.deleteQuestion(id);
    }

    @Override
    public void createQuestion(String question, Integer questionnaire) {
        questionDAO.createQuestion(question, questionnaire);
    }
}
