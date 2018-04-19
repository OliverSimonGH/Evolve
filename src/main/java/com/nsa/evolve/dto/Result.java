package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by c1633899 on 23/11/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result{

    private Integer fkAssessment;
    private Integer fkModule;
    private Integer score;
    private Integer fkCompany;
}
