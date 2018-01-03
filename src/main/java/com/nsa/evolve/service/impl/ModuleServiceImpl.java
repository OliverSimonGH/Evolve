package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.ModuleDAO;
import com.nsa.evolve.dto.Module;
import com.nsa.evolve.dto.ModuleJoin;
import com.nsa.evolve.dto.ModuleType;
import com.nsa.evolve.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by c1633899 on 29/11/2017.
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDAO moduleDAO;

    @Override
    public List<Module> findAllModulesByCompany(Integer fkCompany) {
        return moduleDAO.findAllModulesByCompany(fkCompany);
    }

    @Override
    public List<ModuleJoin> findAllModules(Integer fkCompany) {
        return moduleDAO.findAllModules(fkCompany);
    }

    @Override
    public List<ModuleType> findModulesNotAdded(Integer fkCompany) {
        return  moduleDAO.findModulesNotAdded(fkCompany);
    }

    @Override
    public int deleteModuleById(Integer id, Integer company) {
        return moduleDAO.deleteModuleById(id, company);
    }

    @Override
    public int addModule(Integer module, Integer company) {
        return moduleDAO.addModule(module, company);
    }

    @Override
    public int getModuleScore(Integer modId){
        return moduleDAO.getModuleScore(modId);
    }
}
