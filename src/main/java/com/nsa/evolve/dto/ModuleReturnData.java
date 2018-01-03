package com.nsa.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by c1645601 on 29/11/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleReturnData {
    private String ModuleName;
    private List<Integer> moduleScores;

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    public List<Integer> getModuleScores() {
        return moduleScores;
    }

    public void setModuleScores(List<Integer> moduleScores) {
        this.moduleScores = moduleScores;
    }
}
