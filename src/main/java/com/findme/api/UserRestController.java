package com.findme.api;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.User;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Log4j
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/edit-user", method = RequestMethod.GET)
    public ResponseEntity<?> editUser(HttpSession session){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.findById(Long.valueOf(loggedUserId)), HttpStatus.OK);
    }

    @RequestMapping(path = "/edit-user", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> editUserSubmit(HttpSession session, @RequestBody User user){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        userService.update(user);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute User user){
        User createdUser = userService.save(user);
        log.info("User with id:"+createdUser.getId()+" was registered.");

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(HttpSession session, HttpServletRequest request){
        User user = userService.login(request.getParameter("email"), request.getParameter("password"));
        log.info("User with id:"+user.getId()+" logged in");

        session.setAttribute("loggedUser", user);
        session.setAttribute("loggedUserId", String.valueOf(user.getId()));
        session.setAttribute("loggedUserName", user.getFirstName()+" "+user.getLastName());

        return new ResponseEntity<>("redirect:/user/"+user.getId(), HttpStatus.OK);
    }

}
