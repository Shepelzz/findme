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
public class HomeController {
    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
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

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute User user){
        try {
            User createdUser = userService.save(user);
            log.info("User with id:"+createdUser.getId()+" was registered.");

            return new ResponseEntity<>( HttpStatus.OK);
        } catch (BadRequestException e){
            log.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(HttpSession session, HttpServletRequest request){
        try {
            User user = userService.login(request.getParameter("email"), request.getParameter("password"));
            log.info("User with id:"+user.getId()+" logged in");

            session.setAttribute("loggedUser", user);
            session.setAttribute("loggedUserId", String.valueOf(user.getId()));
            session.setAttribute("loggedUserName", user.getFirstName()+" "+user.getLastName());

            return new ResponseEntity<>("redirect:/user/"+user.getId(), HttpStatus.OK);
        } catch (BadRequestException e){
            log.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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