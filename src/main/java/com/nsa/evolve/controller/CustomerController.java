package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.AccountDetails;
import com.nsa.evolve.dto.People;
import com.nsa.evolve.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("customer-dashboard")
public class CustomerController {

    private CompanyService companyService;
    private QuestionService questionService;
    private ModuleTypeService moduleTypeService;
    private ModuleService moduleService;
    private AssessmentService assessmentService;
    private static Logger infoLog = LoggerFactory.getLogger("InfoLog");

    @Autowired
    public CustomerController(CompanyService companyService, QuestionService questionService, ModuleTypeService moduleTypeService, ModuleService moduleService, AssessmentService assessmentService) {
        this.companyService = companyService;
        this.questionService = questionService;
        this.moduleTypeService = moduleTypeService;
        this.moduleService = moduleService;
        this.assessmentService = assessmentService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getDashboard(Model model){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        infoLog.info("Customer with account ID {} visited their dashboard", account.getId());
        People people = account.getPeople();
        model.addAttribute("title", "Dashboard");
        model.addAttribute("loginType", people.getFkType());
        model.addAttribute("modules", moduleService.findAllModules(people.getFkCompany()));
        return "webpage/customer_dashboard";
    }



    @RequestMapping(value = "/questionnaire", method = RequestMethod.GET)
    public String generateQuestionnaire(@RequestParam("q") Integer id, @RequestParam("f") Integer fk, Model model, HttpSession session) {
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        People people = account.getPeople();
        infoLog.info("Customer with account ID {} the questionnaire with ID {}", account.getId(), fk);

        model.addAttribute("num", assessmentService.countAssessments(people.getFkCompany(), fk));
        model.addAttribute("title", "Customer Questionnaire");
        return "webpage/customer_questions";
    }

}
