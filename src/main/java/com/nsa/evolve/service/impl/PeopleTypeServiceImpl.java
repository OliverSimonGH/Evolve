package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.PeopleTypeDAO;
import com.nsa.evolve.dto.PeopleType;
import com.nsa.evolve.service.PeopleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by c1633899 on 28/11/2017.
 */
@Service
public class PeopleTypeServiceImpl implements PeopleTypeService{

    private PeopleTypeDAO peopleTypeDAO;

    @Autowired
    public PeopleTypeServiceImpl(PeopleTypeDAO peopleTypeDAO) {
        this.peopleTypeDAO = peopleTypeDAO;
    }

    @Override
    public List<PeopleType> findAllPeopleType() {
        return peopleTypeDAO.findAllPeopleType();
    }
}
