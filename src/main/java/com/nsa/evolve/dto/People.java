package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer fkCompany;
    private Integer fkAccount;
    private Integer fkType;

    public People(String firstName, String lastName, Integer fkCompany, Integer fkAccount, Integer fkType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fkCompany = fkCompany;
        this.fkAccount = fkAccount;
        this.fkType = fkType;
    }

    public People(People people) {
        this.id = people.getId();
        this.firstName = people.getFirstName();
        this.lastName = people.getLastName();
        this.fkCompany = people.getFkCompany();
        this.fkAccount = people.getFkAccount();
        this.fkType = people.getFkType();
    }
}
