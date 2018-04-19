package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire {

    private Integer id;
    private String name;
    private Integer fkModule;

    public Questionnaire(String name, Integer fkModule) {
        this.name = name;
        this.fkModule = fkModule;
    }
}
