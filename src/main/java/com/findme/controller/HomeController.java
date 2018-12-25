package com.findme.controller;

import com.findme.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(path = "/test-ajax", method = RequestMethod.GET)
    public ResponseEntity<String> testAjax(){

        return new ResponseEntity<>("trouble", HttpStatus.NOT_FOUND);
    }


}
