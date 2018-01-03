package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by c1633899 on 04/12/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleJoin {

    private Integer id;
    private String name;
    private Integer fkModule;

    public ModuleJoin(String name, Integer fkModule) {
        this.name = name;
        this.fkModule = fkModule;
    }
}
