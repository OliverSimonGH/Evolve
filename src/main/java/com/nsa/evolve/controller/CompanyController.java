package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.AccountDetails;
import com.nsa.evolve.dto.Company;
import com.nsa.evolve.dto.ModuleReturnData;
import com.nsa.evolve.form.CustomerForm;
import com.nsa.evolve.form.ModuleForm;
import com.nsa.evolve.form.PDFForm;
import com.nsa.evolve.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by c1633899 on 27/11/2017.
 */
@Controller
@RequestMapping("company-dashboard")
public class CompanyController {

    private PeopleService peopleService;
    private CompanyService companyService;
    private ModuleService moduleService;
    private PDFReport pdfReport;
    private AssessmentService assessmentService;
    private static Logger infoLog = LoggerFactory.getLogger("InfoLog");

    @Autowired
    public CompanyController(PeopleService peopleService, CompanyService companyService, ModuleService moduleService, PDFReport pdfReport, AssessmentService assessmentService) {
        this.peopleService = peopleService;
        this.companyService = companyService;
        this.moduleService = moduleService;
        this.pdfReport = pdfReport;
        this.assessmentService = assessmentService;
    }

    @RequestMapping(value = "/add-customer", method = RequestMethod.GET)
    public String getCustomerForm(Model model, CustomerForm customerForm){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        infoLog.info("Company with account ID {} visited the add customer page", account.getId());
        model.addAttribute("title", "Company Dashboard");
        return "webpage/add-customer";
    }

    @RequestMapping(value = "/modules", method = RequestMethod.GET)
    public String getModulePage(Model model){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = account.getCompany();
        infoLog.info("Company with account ID {} visited the change modules page", account.getId());

        model.addAttribute("title", "Select Modules");
        model.addAttribute("modules", moduleService.findAllModules(company.getId()));
        model.addAttribute("notAdded", moduleService.findModulesNotAdded(company.getId()));
        return "webpage/modules";
    }

    @RequestMapping(value = "/modules/delete", method = RequestMethod.POST)
    public String postModulePage(@ModelAttribute ModuleForm moduleForm){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = account.getCompany();
        moduleService.deleteModuleById(moduleForm.getId(), company.getId());
        infoLog.info("Company with account ID {} deleted the module with ID {}", account.getId(), moduleForm.getId());

        return "redirect:/company-dashboard/modules";
    }

    @RequestMapping(value = "/modules/add", method = RequestMethod.POST)
    public String postDeleteModule(@ModelAttribute ModuleForm moduleForm){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = account.getCompany();
        moduleService.addModule(moduleForm.getId(), company.getId());
        infoLog.info("Company with account ID {} added the module with ID {}", account.getId(), moduleForm.getId());
        return "redirect:/company-dashboard/modules";
    }

    @RequestMapping(value = "/add-customer", method = RequestMethod.POST)
    public String postCustomerForm(@ModelAttribute @Valid CustomerForm customerForm, BindingResult bindingResult) throws MessagingException, NoSuchAlgorithmException {
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = account.getCompany();

        if (bindingResult.hasErrors()) {
            infoLog.info("Company with account ID {} failed to add a customer (regex)", account.getId());
            return "webpage/add-customer";
        }

        peopleService.createPeopleAccount(customerForm.getFirstName(), customerForm.getLastName(), customerForm.getEmail(), company.getId(), customerForm.getType());
        return "redirect:/company-dashboard/add-customer";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCompanyDashboardData(Model model){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = account.getCompany();
        infoLog.info("Company with account ID {} visited the their dashboard", account.getId());

        List<ModuleReturnData> ModuleReturnData = companyService.findModuleScores(company.getId());
        List<Integer> qviScores = companyService.findQviScores(company.getId());
        Company CompanyName = companyService.findCompanyNameById(company.getId());

        model.addAttribute("CompanyName", CompanyName.getName());
        model.addAttribute("ModuleScores", ModuleReturnData );
        model.addAttribute("QviScores", qviScores );

        return "webpage/company_dashboard";
    }


    @RequestMapping(value = "/add-assessment", method = RequestMethod.GET)
    public String addAssessment(Model model){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        infoLog.info("Company with account ID {} added an assessment", account.getId());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        model.addAttribute("Date",localDate);
        return  "webpage/create-assessment";
    }


    @RequestMapping(value = "/company/get-company-qvi/{id}", method = RequestMethod.GET)
    public  @ResponseBody Long getCompanyQviById(@PathVariable int id, Model model){
        return companyService.getCompanyQvi((long) id);
    }

//    https://stackoverflow.com/questions/12837907/what-to-return-if-spring-mvc-controller-method-doesnt-return-value
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/upload-data", method = RequestMethod.POST)
    public void getImage(@ModelAttribute PDFForm form, HttpSession session){
        session.setAttribute("form", form);
    }

    @RequestMapping(value = "/download/pdf", method = RequestMethod.GET)
    public ResponseEntity<byte[]> createDocument(HttpSession session){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        infoLog.info("Company with account ID {} download a pdf with their data", account.getId());

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
    public String createAssessment(Model model){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("account", account);
        assessmentService.createAssessment(account.getCompany().getId());
        return "redirect:/company-dashboard";
    }
}
