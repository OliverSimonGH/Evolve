package com.nsa.evolve.service;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.People;

import javax.mail.MessagingException;

/**
 * Created by c1633899 on 24/11/2017.
 */
public interface PeopleService {

    People findPeopleByAccount(String email, String password);
    boolean createPeopleAccount(String first_name, String last_name, String email, Integer fkCompany, Integer fkType) throws MessagingException;

}
