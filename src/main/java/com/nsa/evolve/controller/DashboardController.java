package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Company;
import com.nsa.evolve.dto.People;
import com.nsa.evolve.form.ModuleForm;
import com.nsa.evolve.service.*;
import org.springframework.stereotype.Controller;
import com.nsa.evolve.dto.Assessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private CompanyService companyService;
    private QuestionService questionService;
    private ModuleTypeService moduleTypeService;
    private ModuleService moduleService;
    private AssessmentService assessmentService;

    @Autowired
    public DashboardController(CompanyService companyService, QuestionService questionService, ModuleTypeService moduleTypeService, ModuleService moduleService, AssessmentService assessmentService) {
        this.companyService = companyService;
        this.questionService = questionService;
        this.moduleTypeService = moduleTypeService;
        this.moduleService = moduleService;
        this.assessmentService = assessmentService;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard(HttpSession session, Model model){
        People people = (People) session.getAttribute("login");
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("title", "Dashboard");
        model.addAttribute("login", people);
        model.addAttribute("loginType", people.getFkType());
        model.addAttribute("modules", moduleService.findAllModules(people.getFkCompany()));
        return "webpage/customer_dashboard";
    }



    @RequestMapping(value = "dashboard/questionnaire", method = RequestMethod.GET)
    public String generateQuestionnaire(@RequestParam("q") Integer id, @RequestParam("f") Integer fk, Model model, HttpSession session) {
        People people = (People) session.getAttribute("login");
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("num", assessmentService.countAssessments(people.getFkCompany(), fk));
        model.addAttribute("account", account);
        model.addAttribute("title", "Customer Questionnaire");
        model.addAttribute("login", people);
        return "webpage/customer_questions";
    }

}
