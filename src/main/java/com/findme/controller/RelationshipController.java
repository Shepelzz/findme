package com.findme.controller;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class RelationshipController {
    private RelationshipDAO relationshipDAO;

    @Autowired
    public RelationshipController(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

    //TODO send friend req
    @RequestMapping(path = "/friend-add/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> addFriend(@PathVariable("userId") String userId){
        return null;
    }

    //TODO remove friend
    @RequestMapping(path = "/friend-remove/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> friendRemove(@PathVariable("userId") String userId){
        return null;
    }

    //TODO accept friend req
    @RequestMapping(path = "/friend-request-accept/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> friendRequestAccept(HttpSession session,@PathVariable("userId") String userId){
            return new ResponseEntity<>( HttpStatus.OK);
    }

    //TODO reject friend req
    @RequestMapping(path = "/friend-request-reject/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> friendRequestReject(@PathVariable("userId") String userId){
        return null;
    }
}
