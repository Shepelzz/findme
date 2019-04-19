package com.findme.controller;

import com.findme.dao.MessageDAO;
import com.findme.dao.PostDAO;
import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.model.Relationship;
import com.findme.service.UserService;
import com.findme.types.RelationshipStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Log4j
@Controller
public class UserController {
    private UserService userService;
    private RelationshipDAO relationshipDAO;
    private PostDAO postDAO;
    private MessageDAO messageDAO;

    @Autowired
    public UserController(UserService userService, RelationshipDAO relationshipDAO, PostDAO postDAO, MessageDAO messageDAO) {
        this.userService = userService;
        this.relationshipDAO = relationshipDAO;
        this.postDAO = postDAO;
        this.messageDAO = messageDAO;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(HttpSession session, Model model, @PathVariable String userId){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/forbidden";
        }

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
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "profile";
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
