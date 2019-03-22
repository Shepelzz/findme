package com.findme.controller;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.service.MessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Log4j
@Controller
public class MessageController {
    private MessageService messageService;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public MessageController(MessageService messageService, RelationshipDAO relationshipDAO) {
        this.messageService = messageService;
        this.relationshipDAO = relationshipDAO;
    }


    @RequestMapping(path = {"/messages/{userId}","/messages"}, method = RequestMethod.GET)
    public String messagesByUser(HttpSession session, Model model, @PathVariable String userId){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/forbidden";
        }

        model.addAttribute("conversationList", relationshipDAO.getFriendsList(loggedUserId));
        model.addAttribute("messagesList", messageService.getMessageList(loggedUserId, userId));
        model.addAttribute("loggedUserId", loggedUserId);
        if(userId != null)
            model.addAttribute("recipientId", userId);

        return "messages";
    }

//    @RequestMapping(path = "/messages", method = RequestMethod.GET)
//    public String messages(HttpSession session, Model model){
//        String loggedUserId = (String) session.getAttribute("loggedUserId");
//        if(loggedUserId==null) {
//            log.warn("User is not authorized");
//            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
//            return "errors/forbidden";
//        }
//
//        model.addAttribute("friendsList", relationshipDAO.getFriendsList(loggedUserId));
//        model.addAttribute("loggedUserId", loggedUserId);
//        model.addAttribute("recipientId", null);
//
//        return "messages";
//    }

}
