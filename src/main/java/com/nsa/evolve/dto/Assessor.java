package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Assessor(Assessor assessor) {
        this.id = assessor.getId();
        this.name = assessor.getName();
        this.fkAccount = assessor.getFkAccount();
    }
}
