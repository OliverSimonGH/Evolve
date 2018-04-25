package com.nsa.evolve.form;

import com.nsa.evolve.validator.PassValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by c1633899 on 08/12/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {

    @NotNull
    private String current;

    @PassValid
    private String latest;
}
