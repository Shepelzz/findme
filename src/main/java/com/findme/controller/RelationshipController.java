package com.findme.controller;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class RelationshipController {
    private RelationshipDAO relationshipDAO;

    @Autowired
    public RelationshipController(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

//    @RequestMapping(path = "/user/{userId}", method = RequestMethod.POST)
//    public ResponseEntity<String> incomingReqList(HttpSession session, Model model, @PathVariable String userId){
//        User currentUser = (User) session.getAttribute("user");
//        if(currentUser==null) {
//            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
//            return "errors/badRequest";
//        }
//        try {
////            model.addAttribute("user", userService.findById(Long.valueOf(userId)));
////            model.addAttribute("profileStatus", currentUser.getId().equals(Long.valueOf(userId)) ? RelationshipStatus.OWNER : RelationshipStatus.FRIENDS);
//
//            //TODO
//            //add incoming req
//            //add outgoing req
//            //add friends list
//
//            return "profile";
//        } catch (NumberFormatException e){
//            model.addAttribute("error", e);
//            return "errors/badRequest";
//        } catch (InternalServerError ise){
//            model.addAttribute("error", ise);
//            return "errors/internalServerError";
//        } catch (NotFoundException nofe){
//            model.addAttribute("error", nofe);
//            return "errors/notFound";
//        }
//    }

}
