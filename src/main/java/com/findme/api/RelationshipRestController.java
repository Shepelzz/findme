package com.findme.api;

import com.findme.service.RelationshipService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Log4j
@RestController
public class RelationshipRestController {
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipRestController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/save-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestSave(HttpSession session,
                                              @RequestParam(required=false,name="userId") String userId){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        relationshipService.saveRelationship((String) session.getAttribute("loggedUserId"), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/update-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> requestUpdate(HttpSession session,
                                                @RequestParam(required=false,name="userId") String userId,
                                                @RequestParam(required=false,name="status") String status){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        relationshipService.updateRelationship((String) session.getAttribute("loggedUserId"), userId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
