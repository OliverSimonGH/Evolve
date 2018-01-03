package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assessor {

    private Integer id;
    private String name;
    private Integer fkAccount;

    public Assessor(String name, Integer fkAccount) {
        this.name = name;
        this.fkAccount = fkAccount;
    }
}
