package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.AccountDetails;
import com.nsa.evolve.dto.Score;
import com.nsa.evolve.dto.ShortCompanyData;
import com.nsa.evolve.form.AssessmentForm;
import com.nsa.evolve.form.QuestionDeleteForm;
import com.nsa.evolve.form.QuestionForm;
import com.nsa.evolve.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/assessor-dashboard")
public class AssessorController {

    private ResultService resultService;
    private CompanyService companyService;
    private QuestionnaireService questionnaireService;
    private QuestionService questionService;
    private ScoreService scoreService;
    private AssessorService assessorService;
    private AssessmentService assessmentService;
    private static Logger infoLog = LoggerFactory.getLogger("InfoLog");

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

    @RequestMapping("")
    public String getAssessorDashboard(Model model) {
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        infoLog.info("Assessor with account ID {} visited the assessor dashboard", account.getId());
        model.addAttribute("companies", companyService.findCompanyByAssessorId(account.getAssessor().getId()));
        model.addAttribute("questionnaires", questionnaireService.findAllQuestionnaires());
        model.addAttribute("title", "AssessorDashboard");
        return "webpage/assessor_dashboard";
    }

    @RequestMapping("/{id}/{assessment}")
    public String getCompanyModules(@PathVariable("id") Integer id, @PathVariable("assessment") Integer assessment, HttpSession session, Model model) {
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        infoLog.info("Assessor with account ID {} visited the company of ID {} assessment of ID {}", account.getId(), id, assessment);
        model.addAttribute("company", companyService.findCompanyNameById(id));
        model.addAttribute("modules", assessmentService.getRMADataByAssessment(assessment));
        model.addAttribute("assessment", assessment);
        model.addAttribute("title", "Select a module...");
        return "webpage/assessor_company_interface";
    }

    @RequestMapping("/{id}")
    public String getCompanyAssessments(@PathVariable("id") Integer id, HttpSession session, Model model) {
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("company", companyService.findCompanyNameById(id));
        model.addAttribute("assessments", assessmentService.getAllByCompanyId(id));
        model.addAttribute("title", "Company Assessments");
        infoLog.info("Assessor with account ID {} visited the company of ID {} assessments", account.getId(), id);

        return "webpage/assessor_company_assessment";
    }

    @RequestMapping(value = "/{id}/{assessment}/questions", method = RequestMethod.GET)
    public String generateQuestionnaire(@PathVariable("id") Integer id, @PathVariable("assessment") Integer assessment, @RequestParam("q") Integer q, @RequestParam("f") Integer fk, @RequestParam("r") Integer r, Model model) {
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("company", companyService.findCompanyNameById(id));
        model.addAttribute("questions", questionService.findAllQuestionsByQuestionnaire(q));
        model.addAttribute("title", "Verify Questionnaire");
        model.addAttribute("assessment", assessment);
        model.addAttribute("module", fk);
        model.addAttribute("result", r);
        infoLog.info("Assessor with account ID {} visited the company of ID {}, assessment of ID {}, Module of ID {} questions and answers", account.getId(), id, assessment, fk);

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
                                 @RequestParam int idAttr, Model model){
        Long companyQvi = companyService.getCompanyQvi((long) id);

        return companyQvi;
    }

    @RequestMapping(value = "/assessor-compare", method = RequestMethod.GET)
    public String getCompanyAverage(Model model, HttpSession session){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = account.getAssessor().getId();
        List<ShortCompanyData> assessorCompanies = assessorService.getAssessorCompanies((long)id);
        Long industryAverage = companyService.getIndustryAverage();
        String assessorName = assessorService.getAssessorName((long)id);
        model.addAttribute("AssessorName",assessorName);
        model.addAttribute("IndustryAverage", industryAverage );
        model.addAttribute("AssessorCompanies", assessorCompanies );
        infoLog.info("Assessor with account ID {} is comparing companies", account.getId());
        return "webpage/assessor-compare";
    }

    @RequestMapping(value = "/assessment/approve/{id}", method = RequestMethod.POST)
    public String approveAssessment(@PathVariable("id") Integer id, AssessmentForm assessmentForm){
        assessmentService.approveAssessment(assessmentForm.getAssessmentId());
        return "redirect:/assessor-dashboard/" + id;
    }

    @RequestMapping(value = "/questionnaire/{id}", method = RequestMethod.GET)
    public String getQuestionnairePage(@ModelAttribute("question") String question, QuestionForm questionForm, @PathVariable("id") Integer id, HttpSession session, Model model){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("questions", questionService.findAllQuestionsByQuestionnaire(id));
        model.addAttribute("title", "Edit Questionnaire");
        model.addAttribute("id", id);
        model.addAttribute("question", question);
        infoLog.info("Assessor with account ID {} visited the questionnaire of ID {}", account.getId(), id);
        return "webpage/assessor-questionnaire";
    }

    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    public String createQuestion(@ModelAttribute @Valid QuestionForm questionForm, BindingResult bindingResult, RedirectAttributes ra){
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("question", "Question must contain 10 characters");
            return "redirect:/assessor-dashboard/questionnaire/" + questionForm.getId();
        }

        questionService.createQuestion(questionForm.getQuestion(), questionForm.getId());
        return "redirect:/assessor-dashboard/questionnaire/" + questionForm.getId();
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

