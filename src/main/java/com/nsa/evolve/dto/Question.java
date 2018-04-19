package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Integer id;
    private String question;
    private Integer fkQuestionnaire;

    public Question(String question, Integer fkQuestionnaire) {
        this.question = question;
        this.fkQuestionnaire = fkQuestionnaire;
    }
}
