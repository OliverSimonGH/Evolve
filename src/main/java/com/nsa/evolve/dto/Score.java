package com.nsa.evolve.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    private Integer id;
    private Integer score;
    private String comment;
    private Integer fkQuestion;
    private Integer fkModule;
    private Integer fkResult;

    public Score(Integer score, String comment, Integer fkQuestion, Integer fkModule, Integer fkResult) {
        this.score = score;
        this.comment = comment;
        this.fkQuestion = fkQuestion;
        this.fkModule = fkModule;
        this.fkResult = fkResult;
    }
}
