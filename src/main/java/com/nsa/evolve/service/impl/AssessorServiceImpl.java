package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.AssessorDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Assessor;
import com.nsa.evolve.dto.Roles;
import com.nsa.evolve.dto.ShortCompanyData;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
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
        Account account = accountService.findByEmail(email);

        if (account != null) return assessorDAO.findAssessorByAccount(account.getId());
        else return null;
    }


    @Override
    @Transactional
    public Boolean createAssessorAccount(String first_name, String email, String password) throws NoSuchAlgorithmException {
        Boolean result = accountService.createAccount(email, password);

        if (result){
            Account account = accountService.findByEmail(email);
            accountService.insertRoles(account.getId(), Roles.ROLE_ASSESSOR);
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
