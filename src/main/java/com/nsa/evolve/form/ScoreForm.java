package com.nsa.evolve.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by c1633899 on 20/11/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreForm {

    private Integer score;
    private String comment;
    private Integer fkQuestion;
    private Integer fkModule;
    private Integer num;
}

