package com.nsa.evolve.service.impl;

import com.nsa.evolve.classes.ShortCompanyData;
import com.nsa.evolve.dao.AssessorDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Assessor;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Service
public class AssessorServiceImpl implements AssessorService {

    private AssessorDAO assessorDAO;
    private AccountService accountService;

    @Autowired
    public AssessorServiceImpl(AssessorDAO assessorDAO, AccountService accountService) {
        this.assessorDAO = assessorDAO;
        this.accountService = accountService;
    }

    @Override
    public Assessor findAssessorByAccount(String email, String password) {
        Account account = accountService.findByEmailAndPassword(email, password);

        if (account != null) return assessorDAO.findAssessorByAccount(account.getId());
        else return null;
    }


    @Override
    public Boolean createAssessorAccount(String first_name, String email, String password){
        Boolean result = accountService.createAccount(email, password, 3);

        if (result){
            Account account = accountService.findByEmailAndPassword(email, password);
            assessorDAO.createAssessorAccount(first_name, account.getId());
            return true;
        }

        return false;
    }
    @Override
    public List<ShortCompanyData> getAssessorCompanies(Long id){
        return assessorDAO.getAssessorCompanies(id);
    }

    @Override
    public String getAssessorName(Long id){
        return assessorDAO.getAssessorName(id);
    }

}
