package com.findme.controller;

import com.findme.dao.PostDAO;
import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Relationship;
import com.findme.model.User;
import com.findme.service.UserService;
import com.findme.types.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class UserController {
    private UserService userService;
    private RelationshipDAO relationshipDAO;
    private PostDAO postDAO;

    @Autowired
    public UserController(UserService userService, RelationshipDAO relationshipDAO, PostDAO postDAO) {
        this.userService = userService;
        this.relationshipDAO = relationshipDAO;
        this.postDAO = postDAO;
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
            model.addAttribute("btnViewProp", Objects.requireNonNull(getButtonsViewProperty(userId, rel)));
            model.addAttribute("user", userService.findById(Long.valueOf(userId)));
            model.addAttribute("friendsSmallList", relationshipDAO.getSmallFriendsList(userId));
            model.addAttribute("friendsCount", relationshipDAO.getFriendsCount(userId));
            if(rel != null)
                model.addAttribute("relStatus", rel.getStatus());
            if(loggedUserId.equals(userId)){
                model.addAttribute("incomingRequests", relationshipDAO.getIncomingRequests(loggedUserId));
                model.addAttribute("outgoingRequests", relationshipDAO.getOutgoingRequests(loggedUserId));
            }
            model.addAttribute("userPagePostList", postDAO.getPostList(userId));
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
        return "profile_new";
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

    private String getButtonsViewProperty(String userToId, Relationship rel) throws BadRequestException{
        Long userToIdL;
        try {
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
