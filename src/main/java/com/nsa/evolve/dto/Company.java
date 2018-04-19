package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    private Integer id;
    private String name;
    private Integer fkAssessor;
    private Integer fkAccount;

    public Company(String name, Integer fkAssessor, Integer fkAccount) {
        this.name = name;
        this.fkAssessor = fkAssessor;
        this.fkAccount = fkAccount;
    }

    public Company(Integer id, String name, Integer fkAccount) {
        this.id = id;
        this.name = name;
        this.fkAccount = fkAccount;
    }

    public Company(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.fkAssessor = company.getFkAssessor();
        this.fkAccount = company.getFkAccount();
    }
}
