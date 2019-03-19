package com.findme.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class Controller {
    private DAO dao;

    @Autowired
    public Controller(DAO dao) {
        this.dao = dao;
    }


    @RequestMapping(path = "/messages/get-conversations", method = RequestMethod.GET)
    public ResponseEntity<?> getTest(HttpSession session) {
        String loggedUserId = (String) session.getAttribute("loggedUserId");

        List<ConversationInfo> list = dao.getConversations(Long.valueOf(loggedUserId));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
