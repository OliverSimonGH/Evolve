package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by c1633899 on 05/12/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleType {

    private Integer id;
    private String name;

    public ModuleType(String name) {
        this.name = name;
    }
}
