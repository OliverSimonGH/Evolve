package com.nsa.evolve.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
