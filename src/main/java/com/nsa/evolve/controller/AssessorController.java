package com.nsa.evolve.controller;

import com.nsa.evolve.classes.ShortCompanyData;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Assessor;
import com.nsa.evolve.dto.Score;
import com.nsa.evolve.form.AssessmentForm;
import com.nsa.evolve.form.QuestionDeleteForm;
import com.nsa.evolve.form.QuestionForm;
import com.nsa.evolve.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AssessorController {

    private ResultService resultService;
    private CompanyService companyService;
    private QuestionnaireService questionnaireService;
    private QuestionService questionService;
    private ScoreService scoreService;
    private AssessorService assessorService;
    private AssessmentService assessmentService;

    @Autowired
    public AssessorController(ResultService resultService, CompanyService companyService, QuestionnaireService questionnaireService, QuestionService questionService, ScoreService scoreService, AssessorService assessorService, AssessmentService assessmentService) {
        this.resultService = resultService;
        this.companyService = companyService;
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;
        this.scoreService = scoreService;
        this.assessorService = assessorService;
        this.assessmentService = assessmentService;
    }

    @ResponseBody
    @RequestMapping("/api/interface")
    public Integer getDashboard(@RequestParam("module") Integer module, @RequestParam("company") Integer company){
        return resultService.findTotalQuestionnaire(module, company);
    }

    @RequestMapping("/dashboardAssessor")
    public String getAssessorDashboard(HttpSession session, Model model) {
        Assessor assessor = (Assessor) session.getAttribute("login");
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("companies", companyService.findCompanyByAssessorId(assessor.getId()));
        model.addAttribute("questionnaires", questionnaireService.findAllQuestionnaires());
        model.addAttribute("title", "AssessorDashboard");
        model.addAttribute("login", assessor);
        return "webpage/assessor_dashboard";
    }

    @RequestMapping("/dashboardAssessor/{id}/{assessment}")
    public String getCompanyModules(@PathVariable("id") Integer id, @PathVariable("assessment") Integer assessment, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("company", companyService.findCompanyNameById(id));
        model.addAttribute("modules", assessmentService.getRMADataByAssessment(assessment));
        model.addAttribute("assessment", assessment);
        model.addAttribute("title", "Select a module...");
        return "webpage/assessor_company_interface";
    }

    @RequestMapping("/dashboardAssessor/{id}")
    public String getCompanyAssessments(@PathVariable("id") Integer id, HttpSession session, Model model) {
        model.addAttribute("title", "Company");
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("company", companyService.findCompanyNameById(id));
        model.addAttribute("assessments", assessmentService.getAllByCompanyId(id));
        model.addAttribute("title", "Company Assessments");
        return "webpage/assessor_company_assessment";
    }

    @RequestMapping(value = "/dashboardAssessor/{id}/{assessment}/questions", method = RequestMethod.GET)
    public String generateQuestionnaire(@PathVariable("id") Integer id, @PathVariable("assessment") Integer assessment, @RequestParam("q") Integer q, @RequestParam("f") Integer fk, @RequestParam("r") Integer r, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("company", companyService.findCompanyNameById(id));
        model.addAttribute("questions", questionService.findAllQuestionsByQuestionnaire(q));
        model.addAttribute("title", "Verify Questionnaire");
        model.addAttribute("assessment", assessment);
        model.addAttribute("module", fk);
        model.addAttribute("result", r);
        return "webpage/assessor_approve";
    }

    @ResponseBody
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public List<Score> findAllScoresByModuleAndQuestion(@RequestParam("module") Integer module, @RequestParam("question") Integer question, @RequestParam("result") Integer result){
        return scoreService.findAllByModule(module, question, result);
    }

    @RequestMapping(value = "/company-qvi/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Long getCompanyDashboardData(@PathVariable(value = "id") int id,
                                 @RequestParam int idAttr, Model model, HttpSession session){
        Long companyQvi = companyService.getCompanyQvi((long) id);

        return companyQvi;
    }

    @RequestMapping(value = "/Assessor-compare", method = RequestMethod.GET)
    public String getCompanyAverage(Model model, HttpSession session){
        Assessor assessor = (Assessor) session.getAttribute("login");
        int id = assessor.getId();
        List<ShortCompanyData> assessorCompanies = assessorService.getAssessorCompanies((long)id);
        Long industryAverage = companyService.getIndustryAverage();
        String assessorName = assessorService.getAssessorName((long)id);
        model.addAttribute("AssessorName",assessorName);
        model.addAttribute("IndustryAverage", industryAverage );
        model.addAttribute("AssessorCompanies", assessorCompanies );

        return "webpage/assessor-compare";
    }

    @RequestMapping(value = "/assessment/approve/{id}", method = RequestMethod.POST)
    public String approveAssessment(@PathVariable("id") Integer id, AssessmentForm assessmentForm){
        assessmentService.approveAssessment(assessmentForm.getAssessmentId());
        return "redirect:/dashboardAssessor/" + id;
    }

    @RequestMapping(value = "/dashboardAssessor/questionnaire/{id}", method = RequestMethod.GET)
    public String getQuestionnairePage(@PathVariable("id") Integer id, HttpSession session, Model model){
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("account", account);
        model.addAttribute("questions", questionService.findAllQuestionsByQuestionnaire(id));
        model.addAttribute("title", "Edit Questionnaire");
        model.addAttribute("id", id);
        return "webpage/assessor-questionnaire";
    }

    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    public String createQuestion(QuestionForm questionForm){
        questionService.createQuestion(questionForm.getQuestion(), questionForm.getId());
        return "redirect:/dashboardAssessor/questionnaire/" + questionForm.getId();
    }

    @ResponseBody
    @RequestMapping(value = "/question/update", method = RequestMethod.POST)
    public void updateQuestion(QuestionForm questionForm){
        questionService.editQuestion(questionForm.getId(), questionForm.getQuestion());
    }

    @ResponseBody
    @RequestMapping(value = "/question/delete", method = RequestMethod.POST)
    public void deleteQuestion(QuestionDeleteForm questionForm){
        questionService.deleteQuestion(questionForm.getId());
    }
}

