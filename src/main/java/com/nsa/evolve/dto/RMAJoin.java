package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by c1633899 on 13/12/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RMAJoin {

    private Integer fkModule;
    private Integer module;
    private String name;

}
