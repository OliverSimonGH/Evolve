package com.nsa.evolve.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by c1633899 on 29/11/2017.
 */
@Data
public class ModuleForm {

    @NotNull
    private Integer id;
}
