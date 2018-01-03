package com.nsa.evolve.form;

import lombok.Data;

/**
 * Created by c1633899 on 20/11/2017.
 */
@Data
public class ScoreForm {

    private Integer score;
    private String comment;
    private Integer fkQuestion;
    private Integer fkModule;
    private Integer num;
}
