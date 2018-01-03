package com.nsa.evolve.dao;

import com.nsa.evolve.dto.People;

public interface PeopleDAO {

    People findPeopleByAccount(Integer foreignKey);
    void createPeopleAccount(String firstName, String lastName, Integer fkCompany, Integer fkAccount, Integer fkType);
}
