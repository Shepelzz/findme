package com.findme.controller;

import com.findme.exception.InternalServerError;
import com.findme.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class RelationshipController {
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    //add friend
    @RequestMapping(path = "/friend-add/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> addFriend(HttpSession session, @PathVariable("userId") String userId){
        try {
            relationshipService.addFriend((String) session.getAttribute("userId"), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //remove friend
    @RequestMapping(path = "/friend-remove/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> friendRemove(HttpSession session, @PathVariable("userId") String userId){
        try {
            relationshipService.deleteFriend((String) session.getAttribute("userId"), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //accept friend req
    @RequestMapping(path = "/friend-request-accept/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> friendRequestAccept(HttpSession session,@PathVariable("userId") String userId){
        try {
            relationshipService.acceptFriend((String) session.getAttribute("userId"), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //reject friend req
    @RequestMapping(path = "/friend-request-reject/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> friendRequestReject(HttpSession session, @PathVariable("userId") String userId){
        try {
            relationshipService.rejectFriend((String) session.getAttribute("userId"), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //cancel request
    @RequestMapping(path = "/request-cancel/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> cancelRequest(HttpSession session, @PathVariable("userId") String userId){
        try {
            relationshipService.cancelRequest((String) session.getAttribute("userId"), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
