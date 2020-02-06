package com.web.portfolio.controller;

import com.web.portfolio.entity.Investor;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portfolio")
public class LoginController {

    @PersistenceContext
    protected EntityManager em;

    @RequestMapping(value = {"/login"})
    public String login(HttpSession session, 
            @RequestHeader(value = "referer", required = false) String referer, 
            @RequestParam("username") String username, 
            @RequestParam("password") String password) {
        System.out.println(referer);
        try {
            String sql = "Select i From Investor i Where i.username=:username";
            Investor investor = em.createQuery(sql, Investor.class)
                    .setParameter("username", username)
                    .getSingleResult();
            
            if (investor != null && investor.getPassword().equals(password)) {
                session.setAttribute("investor", investor);
                if(referer.contains("login.jsp")) {
                    return "redirect:/portfolio/index.jsp";
                }
                return "redirect:" + referer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.invalidate();
        return "redirect:/portfolio/login.jsp";
    }
    
    @RequestMapping(value = {"/logout"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/portfolio/login.jsp";
    }

}