package com.findme.controller;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
import com.findme.types.RelationshipStatus;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserService userService;
    private UserDAO userDAO;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public UserController(UserService userService, UserDAO userDAO, RelationshipDAO relationshipDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.relationshipDAO = relationshipDAO;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(HttpSession session, Model model, @PathVariable String userId){
        User loggedUser = (User) session.getAttribute("user");
        if(loggedUser==null) {
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/badRequest";
        }
        try {
            RelationshipStatus status = RelationshipStatus.OWNER;
            if(!loggedUser.getId().equals(Long.valueOf(userId)))
                status = relationshipDAO.getRelationshipStatus(String.valueOf(loggedUser.getId()), userId);

            model.addAttribute("user", userService.findById(Long.valueOf(userId)));
            model.addAttribute("profileStatus", status);
            if(status == RelationshipStatus.OWNER){
                model.addAttribute("incomingRequests", relationshipDAO.getIncomingRequests(userId));
                model.addAttribute("outgoingRequests", relationshipDAO.getOutgoingRequests(userId));
            }
            model.addAttribute("friendsSmallList", relationshipDAO.getSmallFriendsList(userId));
            model.addAttribute("friendsCount", relationshipDAO.getFriendsCount(userId));

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
    public ResponseEntity<String> registerUser(@ModelAttribute User user){
        try {
            userService.save(user);
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        try {
            User user = userService.login(request.getParameter("email"), request.getParameter("password"));
            session.setAttribute("user", user);
            session.setAttribute("userName", user.getFirstName()+" "+user.getLastName());
            return new ResponseEntity<>("redirect:/user/"+user.getId(), HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

//    @RequestMapping(path = "/edit-user/{userId}", method = RequestMethod.PUT)
//    public @ResponseBody
//    User update(Model model){
//
//        return null;
//    }

    @RequestMapping(path = "/remove-user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        try {
            userService.delete(Long.valueOf(userId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/add-friend/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> addFriend(Session session, @PathVariable String userId){
        return null;
    }


}
