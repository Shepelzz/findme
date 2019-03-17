package com.findme.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private DAO dao;

    @Autowired
    public Controller(DAO dao) {
        this.dao = dao;
    }


    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> getTest() throws Exception {
        List<ConversationInfo> list = dao.getConversations(1L);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
