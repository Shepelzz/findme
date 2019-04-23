package com.findme.api;

import com.findme.dao.MessageDAO;
import com.findme.model.ConversationInfo;
import com.findme.model.Message;
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
    private MessageDAO messageDAO;

    @Autowired
    public MessageRestController(MessageService messageService, MessageDAO messageDAO) {
        this.messageService = messageService;
        this.messageDAO = messageDAO;
    }

    @RequestMapping(path = "/send-message", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> saveMessage(@RequestBody Message message, HttpSession session){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        messageService.save(message);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> editMessage(HttpSession session, @RequestBody Message message){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        messageService.update(message);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMessage(@RequestParam Long messageId, HttpSession session){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        messageService.delete(messageId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/get-messages", method = RequestMethod.GET)
    public ResponseEntity<?> getMessages(HttpSession session, @RequestParam String userToId){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        List<Message> list = messageService.getMessageList(loggedUserId, userToId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/messages/get-conversations", method = RequestMethod.GET)
    public ResponseEntity<?> getTest(HttpSession session) {
        String loggedUserId = (String) session.getAttribute("loggedUserId");

        List<ConversationInfo> list = messageDAO.getConversations(Long.valueOf(loggedUserId));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/messages/get-incoming-messages-count", method = RequestMethod.GET)
    public ResponseEntity<?> getIncomingMessagesCount(HttpSession session){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(messageDAO.getIncomingMessagesCount(loggedUserId), HttpStatus.OK);
    }

}
