package com.findme.api;

import com.findme.model.Message;
import com.findme.model.MessageInfo;
import com.findme.service.MessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j
@RestController
public class MessageRestController {
    private MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = "/send-message", method = RequestMethod.POST)
    public ResponseEntity<String> savePost(@ModelAttribute MessageInfo messageInfo, HttpSession session){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }

        messageService.save(messageInfo);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> editUserSubmit(HttpSession session, @RequestBody MessageInfo messageInfo){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        messageService.update(messageInfo);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePost(@RequestParam String messageId, HttpSession session){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        messageService.delete(Long.valueOf(messageId));
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/get-messages", method = RequestMethod.GET)
    public ResponseEntity<?> getNewsFeed(HttpSession session, @RequestParam String userToId){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        List<Message> list = messageService.getMessageList(loggedUserId, userToId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
