package com.nsa.evolve.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by c1633899 on 21/11/2017.
 */
@Data
public class LoginForm {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
