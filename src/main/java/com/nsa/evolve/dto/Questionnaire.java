package com.nsa.evolve.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
