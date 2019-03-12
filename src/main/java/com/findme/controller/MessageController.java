package com.findme.controller;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Log4j
@Controller
public class MessageController {
    private RelationshipDAO relationshipDAO;

    @Autowired
    public MessageController(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

    @RequestMapping(path = "/messages", method = RequestMethod.GET)
    public String messages(HttpSession session, Model model){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/forbidden";
        }
        model.addAttribute("friendsList", relationshipDAO.getFriendsList(loggedUserId));

        return "messages";
    }

}
