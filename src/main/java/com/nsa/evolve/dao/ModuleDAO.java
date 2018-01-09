package com.nsa.evolve.dao;

import com.nsa.evolve.dto.Module;
import com.nsa.evolve.dto.ModuleJoin;
import com.nsa.evolve.dto.ModuleType;

import java.util.List;

/**
 * Created by c1633899 on 29/11/2017.
 */
public interface ModuleDAO {

    List<Module> findAllModulesByCompany(Integer fkCompany);
    List<ModuleJoin> findAllModules(Integer fkCompany);
    List<ModuleType> findModulesNotAdded(Integer fkCompany);
    ModuleJoin findModuleByID(Integer id);
    int deleteModuleById(Integer id, Integer company);
    int addModule(Integer module, Integer company);
    Integer getModuleScore(Integer modId);
    String getModuleRealName(Integer modID);
}
