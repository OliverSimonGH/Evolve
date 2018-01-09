package com.nsa.evolve.controller;

import com.itextpdf.text.Document;
import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.Company;
import com.nsa.evolve.form.PasswordForm;
import com.nsa.evolve.service.AccountService;
import com.nsa.evolve.service.PDFReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Created by c1633899 on 08/12/2017.
 */
@Controller
public class SettingsController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String getSettingsPage(Model model, HttpSession session, @ModelAttribute("error") String error, @ModelAttribute("success") String success, @ModelAttribute("password") String password){
        Account account = (Account) session.getAttribute("account");

        model.addAttribute("error", error);
        model.addAttribute("success", success);
        model.addAttribute("account", account);
        model.addAttribute("password", password);
        model.addAttribute("title", "Account Settings");
        return "webpage/settings";
    }

    @RequestMapping(value = "/account/updatePassword", method = RequestMethod.PUT)
    public String updateAccountPassword(@ModelAttribute PasswordForm passwordForm, RedirectAttributes redirectAttributes, HttpSession session){
        Account account = (Account) session.getAttribute("account");

        if (passwordForm.getLatest().length() < 5) {
            redirectAttributes.addFlashAttribute("password", "Enter a password that's 5 or more characters long");
            return "redirect:/settings";
        }

        boolean result = accountService.changePassword(passwordForm.getCurrent(), passwordForm.getLatest(), account.getId());

        if (result){
            redirectAttributes.addFlashAttribute("success", "Password Updated");
        } else {
            redirectAttributes.addFlashAttribute("error", "Please enter your current password");
        }

        return "redirect:/settings";
    }
}



