package com.nsa.evolve.form;

import com.nsa.evolve.validator.EmailValid;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by c1633899 on 28/11/2017.
 */
@Data
public class CustomerForm {

    @Size(min = 1, message = "First name must contain at least 1 character")
    private String firstName;

    @Size(min = 1, message = "Last name must contain at least 1 characters")
    private String lastName;

    @EmailValid
    private String email;

    @NotNull
    private Integer type;
}
