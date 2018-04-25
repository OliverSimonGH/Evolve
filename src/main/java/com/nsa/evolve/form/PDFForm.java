package com.nsa.evolve.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by c1633899 on 11/12/2017.
 */
@Data
public class PDFForm {

    @Size(min = 10, message = "Introduction must contain at least 10 characters")
    private String introduction;

    @Size(min = 10, message = "Conclusion must contain at least 10 characters")
    private String conclusion;

    @NotNull
    private String qviScore;

    @NotNull
    private String moduleOne;

    @NotNull
    private String moduleTwo;

    @NotNull
    private String moduleThree;

    @NotNull
    private String moduleFour;

    @NotNull
    private String moduleFive;

}
