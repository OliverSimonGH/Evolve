package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Score;

import java.util.List;

public interface ScoreDAO {

   void insertScoreForComment(Integer score, String comment, Integer fkQuestion, Integer fkModule, Integer fk_result);
   List<Score> findAllByModule(Integer module, Integer question, Integer result);
   void updateScoresForModule(Integer result, Integer module);
}
