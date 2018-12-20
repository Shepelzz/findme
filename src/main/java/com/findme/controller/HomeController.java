package com.findme.controller;

import com.findme.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    private UserDAO userDAO;

    @Autowired
    public HomeController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("userList", userDAO.getFirstUsers());
        return "index";
    }

}
