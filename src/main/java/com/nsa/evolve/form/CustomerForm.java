package com.nsa.evolve.form;

import com.nsa.evolve.validator.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by c1633899 on 28/11/2017.
 */
@Data
public class CustomerForm {

    @Size(min = 2, message = "First name must contain at least 2 characters")
    private String firstName;

    @Size(min = 2, message = "First name must contain at least 2 characters")
    private String lastName;

    @NotNull
    @EmailValid
    private String email;

    @NotNull
    private Integer type;
}
