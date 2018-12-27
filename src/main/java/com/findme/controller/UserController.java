package com.findme.controller;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        } catch (NumberFormatException e){
            model.addAttribute("error", e);
            return "errors/badRequest";
        } catch (InternalServerError ise){
            model.addAttribute("error", ise);
            return "errors/internalServerError";
        } catch (NotFoundException nofe){
            model.addAttribute("error", nofe);
            return "errors/notFound";
        }
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@ModelAttribute User user){
        try {
            userService.save(user);
            return new ResponseEntity<>("User "+user.getFirstName()+" "+user.getLastName()+" registered successfully", HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = "/user/edit/", method = RequestMethod.PUT)
    public @ResponseBody
    User update(Model model){

        return null;
    }

    @RequestMapping(path = "/user/remove/{userId}", method = RequestMethod.DELETE)
    public @ResponseBody
    String delete(Model model, @PathVariable String userId){

        return null;
    }
}
