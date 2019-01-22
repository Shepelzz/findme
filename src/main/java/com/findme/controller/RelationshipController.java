package com.findme.controller;

import com.findme.exception.InternalServerError;
import com.findme.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class RelationshipController {
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }



    @RequestMapping(path = "/request-save", method = RequestMethod.POST)
    public ResponseEntity<String> requestSave(HttpSession session, HttpServletRequest request){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            System.out.println("save - "+request.getParameter("userId"));
            relationshipService.relationshipSave((String) session.getAttribute("loggedUserId"), request.getParameter("userId"));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/request-update", method = RequestMethod.POST)
    public ResponseEntity<String> requestUpdate(HttpSession session, HttpServletRequest request){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            System.out.println("update - "+request.getParameter("userId")+" - "+request.getParameter("status"));
            relationshipService.relationshipUpdate((String) session.getAttribute("loggedUserId"), request.getParameter("userId"), request.getParameter("status"));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
