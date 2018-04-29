package com.nsa.evolve.security;

import com.nsa.evolve.dao.AssessorDAO;
import com.nsa.evolve.dao.CompanyDAO;
import com.nsa.evolve.dao.PeopleDAO;
import com.nsa.evolve.dto.AccountDetails;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Configuration
@NoArgsConstructor
public class AuthenticationSuccessConfig implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private AssessorDAO assessorDAO;
    private PeopleDAO peopleDAO;
    private CompanyDAO companyDAO;

    @Autowired
    public AuthenticationSuccessConfig(AssessorDAO assessorDAO, PeopleDAO peopleDAO, CompanyDAO companyDAO) {
        this.assessorDAO = assessorDAO;
        this.peopleDAO = peopleDAO;
        this.companyDAO = companyDAO;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetURL = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetURL);

    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isCompany = false, isAssessor = false, isCustomer = false;
        AccountDetails account = (AccountDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_CUSTOMER")) {
                account.setPeople(peopleDAO.findPeopleByAccount(account.getId()));
                isCustomer = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ASSESSOR")) {
                account.setAssessor(assessorDAO.findAssessorByAccount(account.getId()));
                isAssessor = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_COMPANY")) {
                account.setCompany(companyDAO.findCompanyByAccount(account.getId()));
                isCompany = true;
                break;
            }

    }

        if (isCompany) return "/company-dashboard";
        else if (isAssessor) return "/assessor-dashboard";
        else if (isCustomer) return "/customer-dashboard";
        else throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
