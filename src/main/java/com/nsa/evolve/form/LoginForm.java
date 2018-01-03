package com.nsa.evolve.form;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by c1633899 on 21/11/2017.
 */
@Data
public class LoginForm {

    private String email;
    private String password;
}
