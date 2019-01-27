package com.findme.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.service.RelationshipService;
import com.findme.types.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class RelationshipController {
    private RelationshipService relationshipService;
    private ObjectMapper objectMapper;

    @Autowired
    public RelationshipController(RelationshipService relationshipService, ObjectMapper objectMapper) {
        this.relationshipService = relationshipService;
        this.objectMapper = objectMapper;
    }


    @RequestMapping(path = "/save-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestSave(HttpSession session, @RequestParam String userId, String status){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            relationshipService.relationshipProcess((String) session.getAttribute("loggedUserId"), userId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/update-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestUpdate(HttpSession session, @RequestParam String userId, String status){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        try {
            relationshipService.relationshipProcess((String) session.getAttribute("loggedUserId"), userId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
