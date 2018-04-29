package com.nsa.evolve.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by c1633899 on 13/12/2017.
 */
@Data
public class QuestionForm {

    @NotNull
    private Integer id;

    @NotNull
    @Size(min = 10, message = "Questions must contain at least 10 characters")
    private String question;
}
