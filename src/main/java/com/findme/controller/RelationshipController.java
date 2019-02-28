package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.service.RelationshipService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Log4j
@Controller
public class RelationshipController {
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/save-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestSave(HttpSession session,
                                              @RequestParam(required=false,name="userId") String userId){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        try {
            relationshipService.saveRelationship((String) session.getAttribute("loggedUserId"), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e){
            log.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/update-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestUpdate(HttpSession session,
                                                @RequestParam(required=false,name="userId") String userId,
                                                @RequestParam(required=false,name="status") String status){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        try {
            relationshipService.updateRelationship((String) session.getAttribute("loggedUserId"), userId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalServerError e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e){
            log.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
