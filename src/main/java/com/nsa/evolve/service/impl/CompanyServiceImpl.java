package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.CompanyDAO;
import com.nsa.evolve.dao.ModuleDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Company;
import com.nsa.evolve.dto.Module;
import com.nsa.evolve.dto.ModuleReturnData;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO;
    private AccountService accountService;
    private ModuleDAO moduleDAO;

    @Autowired
    public CompanyServiceImpl(CompanyDAO companyDAO, AccountService accountService,ModuleDAO moduleDAO) {
        this.companyDAO = companyDAO;
        this.accountService = accountService;
        this.moduleDAO = moduleDAO;
    }

    @Override
    public Company findCompanyByAccount(String email, String password) {
        Account account = accountService.findByEmailAndPassword(email, password);

        if (account != null) return companyDAO.findCompanyByAccount(account.getId());
        else return null;
    }

    @Override
    public boolean createCompanyAccount(String name, String email, String password) {
        Boolean result = accountService.createAccount(email, password, 1);

        if (result){
            Account account = accountService.findByEmailAndPassword(email, password);
            companyDAO.createCompanyAccount(name, account.getId());
            return true;
        }

        return false;
    }

    @Override
    public List<Company> findCompanyByAssessorId(Integer fkAssessor) {
        return companyDAO.findCompanyByAssessorId(fkAssessor);
    }

    @Override
    public Company findCompanyNameById(Integer id) {
        return companyDAO.findCompanyNameById(id);

    }

    @Override
    public List<ModuleReturnData> findModuleScores(int id) {
        List<Integer> notTakenReturnList = new ArrayList<>();
        notTakenReturnList.add(0);
        notTakenReturnList.add(0);
        notTakenReturnList.add(0);
        notTakenReturnList.add(0);
        notTakenReturnList.add(0);
        List<Module> moduleList = moduleDAO.findAllModulesByCompany(id);

        List<ModuleReturnData> ModulesReturnDataList = new ArrayList<>();



        for(Module mod : moduleList){
            String modName = moduleDAO.getModuleRealName(mod.getId());
            ModulesReturnDataList.add(new ModuleReturnData(modName, companyDAO.getModuleReturnData((long) mod.getId(), (long) id)));
        }

        while (ModulesReturnDataList.size() <= 5){
            ModulesReturnDataList.add(new ModuleReturnData("Module Not Taken", notTakenReturnList));
        }


        return ModulesReturnDataList;
    }

    @Override
    public List<Integer> findQviScores(int id) {

        List<Integer> qviScores = companyDAO.getQVIScoresForCompany((long) id);

        while (qviScores.size() <= 5){
            qviScores.add(0);
        }

        return qviScores;
    }

    @Override
    public Company findCompanyName(Integer id) {
        return companyDAO.findCompanyNameById(id);
    }

    @Override
    public  Long getCompanyQvi(Long id){
        return companyDAO.getCompanyQvi(id);
    }

    @Override
    public Long getCompanyQvi(String name){
        return companyDAO.getCompanyQvi(name);
    }

    @Override
    public Long getIndustryAverage(){
        return companyDAO.getIndustryAverage();
    }

}
