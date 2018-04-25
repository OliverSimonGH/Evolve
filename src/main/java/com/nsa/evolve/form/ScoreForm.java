package com.nsa.evolve.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by c1633899 on 20/11/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreForm {

    @NotNull
    private Integer score;

    @Size(min = 10, message = "Comment must contain at least 10 characters")
    private String comment;

    @NotNull
    private Integer fkQuestion;

    @NotNull
    private Integer fkModule;

    private Integer num;
}

