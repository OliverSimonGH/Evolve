package com.nsa.evolve.service;

import com.nsa.evolve.dto.Score;

import java.util.List;

/**
 * Created by c1633899 on 20/11/2017.
 */
public interface ScoreService {

    void insertScoreForComment(Integer score, String comment, Integer fkQuestion, Integer fkModule, Integer fk_result);
    List<Score> findAllByModule(Integer module, Integer question, Integer result);
    void updateScoresForModule(Integer result, Integer module);
}
