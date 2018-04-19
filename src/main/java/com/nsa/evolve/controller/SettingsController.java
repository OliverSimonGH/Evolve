package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.People;
import com.nsa.evolve.dto.SecurityContextCustom;
import com.nsa.evolve.form.PasswordForm;
import com.nsa.evolve.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by c1633899 on 08/12/2017.
 */
@Controller
public class SettingsController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String getSettingsPage(Model model, @ModelAttribute("error") String error, @ModelAttribute("success") String success, @ModelAttribute("password") String password){
        Account account = SecurityContextCustom.getAccount();

        model.addAttribute("error", error);
        model.addAttribute("success", success);
        model.addAttribute("account", account);
        model.addAttribute("password", password);
        model.addAttribute("title", "Account Settings");
        return "webpage/settings";
    }

    @RequestMapping(value = "/account/updatePassword", method = RequestMethod.PUT)
    public String updateAccountPassword(@ModelAttribute PasswordForm passwordForm, RedirectAttributes redirectAttributes){
        Account account = SecurityContextCustom.getAccount();

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



