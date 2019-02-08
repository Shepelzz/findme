package com.findme.controller;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.entity.Relationship;
import com.findme.entity.User;
import com.findme.model.PostInfo;
import com.findme.service.PostService;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import com.findme.types.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class UserController {
    private UserService userService;
    private RelationshipDAO relationshipDAO;
    private RelationshipService relationshipService;
    private PostService postService;

    @Autowired
    public UserController(UserService userService, RelationshipDAO relationshipDAO, RelationshipService relationshipService, PostService postService) {
        this.userService = userService;
        this.relationshipDAO = relationshipDAO;
        this.relationshipService = relationshipService;
        this.postService = postService;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(HttpSession session, Model model, @PathVariable String userId){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/forbidden";
        }
        try {
            Relationship rel = relationshipDAO.getRelationship(loggedUserId, userId);
            model.addAttribute("btnViewProp", Objects.requireNonNull(getButtonsViewProperty(loggedUserId, userId, rel)));
            model.addAttribute("user", userService.findById(Long.valueOf(userId)));
            model.addAttribute("friendsSmallList", relationshipDAO.getSmallFriendsList(userId));
            model.addAttribute("friendsCount", relationshipDAO.getFriendsCount(userId));
            if(rel != null)
                model.addAttribute("relStatus", rel.getStatus());
            if(loggedUserId.equals(userId)){
                model.addAttribute("incomingRequests", relationshipDAO.getIncomingRequests(loggedUserId));
                model.addAttribute("outgoingRequests", relationshipDAO.getOutgoingRequests(loggedUserId));
            }
        } catch (BadRequestException e){
            model.addAttribute("error", e);
            return "errors/badRequest";
        } catch (InternalServerError ise){
            model.addAttribute("error", ise);
            return "errors/internalServerError";
        } catch (NotFoundException nofe){
            model.addAttribute("error", nofe);
            return "errors/notFound";
        }
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "profile";
    }

    @RequestMapping(path = "/edit-user", method = RequestMethod.POST)
    public ResponseEntity<String> editUserSubmit(HttpSession session, @ModelAttribute User user){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            userService.update(user);
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError | NumberFormatException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/remove-user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(HttpSession session, @PathVariable String userId){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            userService.delete(Long.valueOf(userId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<String> login(HttpSession session, HttpServletRequest request){
        try {
            User user = userService.login(request.getParameter("email"), request.getParameter("password"));
            session.setAttribute("loggedUser", user);
            session.setAttribute("loggedUserId", String.valueOf(user.getId()));
            session.setAttribute("loggedUserName", user.getFirstName()+" "+user.getLastName());
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

    @RequestMapping(path = "/save-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestSave(HttpSession session, @RequestParam String userId){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            relationshipService.saveRelationship((String) session.getAttribute("loggedUserId"), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/update-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestUpdate(HttpSession session, @RequestParam String userId, String status){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            relationshipService.updateRelationship((String) session.getAttribute("loggedUserId"), userId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/save-post/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute PostInfo postInfo, HttpSession session, @PathVariable String userId){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);

        postInfo.setUserPagePostedId(userId);
        postInfo.setUserPostedId((String) session.getAttribute("loggedUserId"));

        try {
            postService.save(postInfo);
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError | NumberFormatException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(path = "/save-post", method = RequestMethod.GET)
//    public String testget(Model model){
//        model.addAttribute("userId", 1L);
//        return "postFormTest";
//    }


    private String getButtonsViewProperty(String userFromId, String userToId, Relationship rel) throws BadRequestException{
        Long userFromIdL;
        Long userToIdL;
        try {
            userFromIdL = Long.valueOf(userFromId);
            userToIdL = Long.valueOf(userToId);
        } catch (NumberFormatException e) {
            throw new BadRequestException(e.getMessage());
        }
        //add friend(save)
        if(rel == null)
            return "btn-addSave";

        //remove friend
        if(rel.getStatus() == RelationshipStatus.FRIENDS)
            return "btn-remove";

        //request sent
        if(rel.getStatus()==RelationshipStatus.REQUESTED && rel.getUserTo().getId().equals(userToIdL))
            return "btn-sent";

        //request rejected
        if(rel.getStatus()==RelationshipStatus.REJECTED && rel.getUserFrom().getId().equals(userToIdL))
            return "btn-rejected";

        //add friend(update)
        if(rel.getStatus() != RelationshipStatus.REQUESTED && rel.getStatus() != RelationshipStatus.FRIENDS)
            return "btn-addUpd";

        //accept request
        if(rel.getStatus() == RelationshipStatus.REQUESTED && rel.getUserFrom().getId().equals(userToIdL))
            return "btn-accept";

        return null;
    }
}
