package com.nsa.evolve.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by c1633899 on 08/12/2017.
 */
@Data
public class PasswordForm {

    @NotNull
    private String current;

    @NotNull
    @Size(min = 5, message = "Enter a password that's 5 or more characters long")
    private String latest;
}
