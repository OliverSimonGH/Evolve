package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Assessor;
import com.nsa.evolve.dto.Company;
import com.nsa.evolve.dto.ModuleReturnData;
import com.nsa.evolve.form.CustomerForm;
import com.nsa.evolve.form.ModuleForm;
import com.nsa.evolve.form.PDFForm;
import com.nsa.evolve.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by c1633899 on 27/11/2017.
 */
@Controller
public class CompanyController {

    private PeopleTypeService peopleTypeService;
    private PeopleService peopleService;
    private CompanyService companyService;
    private ModuleService moduleService;
    private PDFReport pdfReport;
    private  AssessmentService assessmentService;

    @Autowired
    public CompanyController(PeopleTypeService peopleTypeService, PeopleService peopleService, CompanyService companyService, ModuleService moduleService, PDFReport pdfReport, AssessmentService assessmentService) {
        this.peopleTypeService = peopleTypeService;
        this.peopleService = peopleService;
        this.companyService = companyService;
        this.moduleService = moduleService;
        this.pdfReport = pdfReport;
        this.assessmentService = assessmentService;
    }

    @RequestMapping(value = "/company-dashboard/add-customer", method = RequestMethod.GET)
    public String getCustomerForm(Model model, HttpSession session){
        Company company = (Company) session.getAttribute("login");
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("login", company);
        model.addAttribute("title", "Company Dashboard");
        model.addAttribute("types", peopleTypeService.findAllPeopleType());
        return "webpage/add-customer";
    }

    @RequestMapping(value = "/company-dashboard/modules", method = RequestMethod.GET)
    public String getModulePage(Model model, HttpSession session){
        Company company = (Company) session.getAttribute("login");
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("title", "Select Modules");
        model.addAttribute("modules", moduleService.findAllModules(company.getId()));
        model.addAttribute("notAdded", moduleService.findModulesNotAdded(company.getId()));
        model.addAttribute("login", company);
        return "webpage/modules";
    }

    @RequestMapping(value = "/company-dashboard/modules/delete", method = RequestMethod.POST)
    public String postModulePage(@ModelAttribute ModuleForm moduleForm, HttpSession session){
        Company company = (Company) session.getAttribute("login");
        moduleService.deleteModuleById(moduleForm.getId(), company.getId());
        return "redirect:/company-dashboard/modules";
    }

    @RequestMapping(value = "/company-dashboard/modules/add", method = RequestMethod.POST)
    public String postDeleteModule(@ModelAttribute ModuleForm moduleForm, HttpSession session){
        System.out.println(moduleForm.getId());
        Company company = (Company) session.getAttribute("login");
        moduleService.addModule(moduleForm.getId(), company.getId());
        return "redirect:/company-dashboard/modules";
    }

    @RequestMapping(value = "/company-dashboard/add-customer", method = RequestMethod.POST)
    public String postCustomerForm(@ModelAttribute CustomerForm customerForm, HttpSession httpSession) throws MessagingException{
        Company company = (Company) httpSession.getAttribute("login");
        peopleService.createPeopleAccount(customerForm.getFirstName(), customerForm.getLastName(), customerForm.getEmail(), company.getId(), customerForm.getType());
        return "redirect:/company-dashboard/add-customer";
    }

    @RequestMapping(value = "/company-dashboard", method = RequestMethod.GET)
    public String getCompanyDashboardData(Model model, HttpSession session){
        Company company = (Company) session.getAttribute("login");
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("login", company);

        List<ModuleReturnData> ModuleReturnData = new ArrayList<>();
        List<Integer> qviScores = new ArrayList<>();
        ModuleReturnData = companyService.findModuleScores(company.getId());
        qviScores = companyService.findQviScores(company.getId());
        Company CompanyName = companyService.findCompanyNameById(company.getId());
        model.addAttribute("CompanyName", CompanyName.getName());

        model.addAttribute("ModuleScores", ModuleReturnData );
        model.addAttribute("QviScores", qviScores );


        return "webpage/company_dashboard";
    }


    @RequestMapping(value = "/company-dashboard/add-assessment", method = RequestMethod.GET)
    public String addAssessment(Model model){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        model.addAttribute("Date",localDate);
        return  "webpage/create-assessment";
    }


    @RequestMapping(value = "/company/get-company-qvi/{id}", method = RequestMethod.GET)
    public  @ResponseBody Long getCompanyQviById(@PathVariable int id, Model model, HttpSession session){
        System.out.println(id);
        return companyService.getCompanyQvi((long) id);
    }

//    https://stackoverflow.com/questions/12837907/what-to-return-if-spring-mvc-controller-method-doesnt-return-value
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
    public void getImage(PDFForm form, HttpSession session){
        session.setAttribute("form", form);
    }

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public ResponseEntity<byte[]> createDocument(HttpSession session){
        PDFForm form = (PDFForm) session.getAttribute("form");
        byte[] content = pdfReport.createReport(form);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(content, headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/create-assessment", method = RequestMethod.GET)
    public String createAssessment(Model model, HttpSession session){
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        Company company = (Company) session.getAttribute("login");
        assessmentService.createAssessment(company.getId());
        return "redirect:/company-dashboard";
    }
}
