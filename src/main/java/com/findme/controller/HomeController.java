package com.findme.controller;

import com.findme.dao.UserDAO;
import com.findme.exception.InternalServerError;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController {
    private UserDAO userDAO;

    @Autowired
    public HomeController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        try {
            String loggedUserId = (String) session.getAttribute("loggedUserId");
            if(loggedUserId != null){
                return "redirect:/user/"+loggedUserId;
            }

            model.addAttribute("userList", userDAO.getFirstUsers());
            return "index";
        } catch (InternalServerError e){
            model.addAttribute("error", e);
            return "errors/internalServerError";
        }
    }
}