package com.nsa.evolve.controller;

import com.nsa.evolve.form.SignupForm;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.AssessorService;
import com.nsa.evolve.service.CompanyService;
import com.nsa.evolve.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AccountController {

    private CompanyService companyService;
    private AccountService accountService;
    private PeopleService peopleService;
    private AssessorService assessorService;

    @Autowired
    public AccountController(CompanyService companyService, AccountService accountService, PeopleService peopleService, AssessorService assessorService) {
        this.companyService = companyService;
        this.accountService = accountService;
        this.peopleService = peopleService;
        this.assessorService = assessorService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignupPage(SignupForm signupForm, Model model, @ModelAttribute("error") String error){
        model.addAttribute("title", "Sign-up");
        model.addAttribute("error", error);
        return "webpage/signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getloginPage(Model model, @ModelAttribute("error") String error, Authentication authentication){
        model.addAttribute("title", "Login");
        model.addAttribute("error", error);
        return "webpage/login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postSignupPage(@ModelAttribute @Valid SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "webpage/signup";
        }

        boolean result = companyService.createCompanyAccount(signupForm.getName(), signupForm.getEmail(), signupForm.getPassword());

        if (result){
            return "redirect:/login";
        }

        else {
            redirectAttributes.addFlashAttribute("error", "E-mail already exists");
            return "redirect:/signup";
        }
    }
}
