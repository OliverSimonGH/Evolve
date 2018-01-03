package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.ModuleTypeDAO;
import com.nsa.evolve.dto.ModuleType;
import com.nsa.evolve.service.ModuleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by c1633899 on 30/11/2017.
 */
@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {

    @Autowired
    private ModuleTypeDAO moduleTypeDAO;

    @Override
    public List<ModuleType> findAllModules() {
        return moduleTypeDAO.findAllModules();
    }
}
