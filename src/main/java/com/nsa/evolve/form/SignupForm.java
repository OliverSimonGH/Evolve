package com.nsa.evolve.form;

import com.nsa.evolve.validator.EmailValid;
import com.nsa.evolve.validator.PassValid;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by c1633899 on 21/11/2017.
 */
@Data
public class SignupForm {

    @Size(min = 3, message = "Company must contain at least 3 characters")
    private String name;

    @EmailValid
    private String email;

    @PassValid
    private String password;
}
