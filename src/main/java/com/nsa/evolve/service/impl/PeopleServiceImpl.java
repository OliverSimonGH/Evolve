package com.nsa.evolve.service.impl;

import com.nsa.evolve.dao.PeopleDAO;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Password;
import com.nsa.evolve.dto.People;
import com.nsa.evolve.dto.Roles;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.MailService;
import com.nsa.evolve.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by c1633899 on 24/11/2017.
 */
@Service
public class PeopleServiceImpl implements PeopleService {

    private PeopleDAO peopleDAO;
    private AccountService accountService;
    private MailService mailService;

    @Autowired
    public PeopleServiceImpl(PeopleDAO peopleDAO, AccountService accountService, MailService mailService) {
        this.peopleDAO = peopleDAO;
        this.accountService = accountService;
        this.mailService = mailService;
    }

    @Override
    public People findPeopleByAccount(Integer foreignKey) {
        return peopleDAO.findPeopleByAccount(foreignKey);
    }

    @Override
    public People findPeopleByAccount(String email, String password) {
        Account account = accountService.findByEmail(email);

        if (account != null) return peopleDAO.findPeopleByAccount(account.getId());
        else return null;
    }

    @Override
    @Transactional
    public boolean createPeopleAccount(String first_name, String last_name, String email, Integer fkCompany, Integer fkType) throws MessagingException, NoSuchAlgorithmException {
        String password = Password.generatePassword(16);
        Boolean result = accountService.createAccount(email, password);

        if (result){
            Account account = accountService.findByEmail(email);
            peopleDAO.createPeopleAccount(first_name, last_name, fkCompany, account.getId(), fkType);
            accountService.insertRoles(account.getId(), Roles.ROLE_CUSTOMER);
            mailService.send(email, "Account registered", "username: " + email + ", password: " + password);
            return true;
        }
        return false;
    }

//    @Override
//    public People findPeopleByAccount(Account account) {
//        return peopleDAO.findByAccount(account);
//    }
}
