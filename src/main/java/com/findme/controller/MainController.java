package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.User;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j
@Controller
public class MainController {
    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
            String loggedUserId = (String) session.getAttribute("loggedUserId");
            if(loggedUserId != null)
                return "redirect:/user/"+loggedUserId;
            model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
            return "index";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        log.info("User with id:"+session.getAttribute("loggedUserId")+" logged out");
        session.invalidate();

        return "redirect:/";
    }


    @RequestMapping(path = "/settings", method = RequestMethod.GET)
    public String settings(Model model, HttpSession session){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/forbidden";
        }
        return "settings";
    }
}