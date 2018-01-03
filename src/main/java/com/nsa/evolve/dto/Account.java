package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Integer id;
    private String email;
    private String password;
    private Integer fkType;

    public Account(String email, String password, Integer fkType) {
        this.email = email;
        this.password = password;
        this.fkType = fkType;
    }
}
