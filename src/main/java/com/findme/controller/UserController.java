package com.findme.controller;

import com.findme.dao.UserDAO;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private UserService userService;
    private UserDAO userDAO;

    @Autowired
    public UserController(UserService userService, UserDAO userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId){
        try {
            model.addAttribute("user", userService.findById(Long.valueOf(userId)));
            return "profile";
        } catch (Exception e){
            model.addAttribute("error", e);
            return "error";
        }

    }

    @RequestMapping(path = "/user/save/", method = RequestMethod.POST)
    public @ResponseBody
    User save(Model model){

        return null;
    }

    @RequestMapping(path = "/user/edit/{userId}", method = RequestMethod.PUT)
    public @ResponseBody
    User update(Model model, @PathVariable String userId){

        return null;
    }

    @RequestMapping(path = "/user/remove/{userId}", method = RequestMethod.DELETE)
    public @ResponseBody
    String delete(Model model, @PathVariable String userId){

        return null;
    }
}
