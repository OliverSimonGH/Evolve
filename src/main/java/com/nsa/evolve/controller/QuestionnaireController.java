package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Question;
import com.nsa.evolve.form.ScoreForm;
import com.nsa.evolve.service.QuestionService;
import com.nsa.evolve.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ScoreService scoreService;

    @RequestMapping(value = "/api/questionnaire", method = RequestMethod.GET)
    public List<Question> getCustomerQuestionnaire(@RequestParam("id") Integer id){
        return questionService.findAllQuestionsByQuestionnaire(id);
    }

    @RequestMapping(value = "/api/questionnaire/question", method = RequestMethod.POST)
    public void insertScoreFromQuestion(ScoreForm scoreForm){
        scoreService.insertScoreForComment(scoreForm.getScore(), scoreForm.getComment(), scoreForm.getFkQuestion(), scoreForm.getFkModule(), scoreForm.getNum());
    }

}
