package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.ScoreDAO;
import com.nsa.evolve.dto.Score;
import com.nsa.evolve.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by c1633899 on 20/11/2017.
 */
@Service
public class ScoreServiceImpl implements ScoreService{

    private ScoreDAO scoreDAO;

    @Autowired
    public ScoreServiceImpl(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    @Override
    public void insertScoreForComment(Integer score, String comment, Integer fkQuestion, Integer fkModule, Integer fk_result) {
        if (comment.equalsIgnoreCase("")){
            scoreDAO.insertScoreForComment(score, null, fkQuestion, fkModule, fk_result);
        } else {
            scoreDAO.insertScoreForComment(score, comment, fkQuestion, fkModule, fk_result);
        }
    }

    @Override
    public List<Score> findAllByModule(Integer module, Integer question, Integer result) {
        return scoreDAO.findAllByModule(module, question, result);
    }

    @Override
    public void updateScoresForModule(Integer result, Integer module) {
        scoreDAO.updateScoresForModule(result, module);
    }
}
