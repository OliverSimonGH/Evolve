package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Account;
import com.nsa.evolve.dto.AccountDetails;
import com.nsa.evolve.form.PasswordForm;
import com.nsa.evolve.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by c1633899 on 08/12/2017.
 */
@Controller
public class SettingsController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String getSettingsPage(PasswordForm passwordForm, Model model, @ModelAttribute("error") String error, @ModelAttribute("success") String success, @ModelAttribute("password") String password){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("error", error);
        model.addAttribute("success", success);
        model.addAttribute("account", account);
        model.addAttribute("password", password);
        model.addAttribute("title", "Account Settings");
        return "webpage/settings";
    }

    @RequestMapping(value = "/account/updatePassword", method = RequestMethod.PUT)
    public String updateAccountPassword(@ModelAttribute @Valid PasswordForm passwordForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Account account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (bindingResult.hasErrors()) {
            return "webpage/settings";
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



