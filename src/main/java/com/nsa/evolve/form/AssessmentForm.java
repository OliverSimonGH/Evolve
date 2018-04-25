package com.nsa.evolve.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by c1633899 on 13/12/2017.
 */
@Data
public class AssessmentForm {

    @NotNull
    private Integer assessmentId;
}
