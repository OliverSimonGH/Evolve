package com.nsa.evolve.service.impl;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.People;
import com.nsa.evolve.dao.PeopleDAO;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.MailService;
import com.nsa.evolve.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

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
    public People findPeopleByAccount(String email, String password) {
        Account account = accountService.findByEmailAndPassword(email, password);

        if (account != null) return peopleDAO.findPeopleByAccount(account.getId());
        else return null;
    }

    @Override
    public boolean createPeopleAccount(String first_name, String last_name, String email, Integer fkCompany, Integer fkType) throws MessagingException{
        String password = accountService.generatePassword(25);
        Boolean result = accountService.createAccount(email, password, 2);

        if (result){
            Account account = accountService.findByEmailAndPassword(email, password);
            peopleDAO.createPeopleAccount(first_name, last_name, fkCompany, account.getId(), fkType);
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
