package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.CompanyDAO;
import com.nsa.evolve.dao.ModuleDAO;
import com.nsa.evolve.dto.*;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static Logger infoLog = LoggerFactory.getLogger("InfoLog");
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
        Account account = accountService.findByEmail(email);

        if (account != null) return companyDAO.findCompanyByAccount(account.getId());
        else return null;
    }

    @Override
    @Transactional
    public boolean createCompanyAccount(String name, String email, String password) throws NoSuchAlgorithmException {
        Boolean result = accountService.createAccount(email, password);

        if (result){
            Account account = accountService.findByEmail(email);
            System.out.println(account.getId());
            accountService.insertRoles(account.getId(), Roles.ROLE_COMPANY);
            companyDAO.createCompanyAccount(name, account.getId());
            infoLog.info("Company account with ID {} has been created", account.getId());
            return true;
        }

        infoLog.info("Company account has failed to be created");
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
        try {
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
        } catch (NullPointerException e){
            throw new NullPointerException("Null pointer exception");
        }

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
