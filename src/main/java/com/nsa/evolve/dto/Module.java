package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by c1633899 on 30/11/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module  {

    private Integer id;
    private Integer fkCompany;
    private Integer fkModule;
    private Integer deleted;

    public Module(Integer fkCompany, Integer fkModule, Integer deleted) {
        this.fkCompany = fkCompany;
        this.fkModule = fkModule;
        this.deleted = deleted;
    }
}
