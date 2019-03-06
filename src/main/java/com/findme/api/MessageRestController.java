package com.findme.api;

import com.findme.exception.BadRequestException;
import lombok.extern.log4j.Log4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Log4j
@RestController
public class MessageRestController {

    @RequestMapping(path = "/messages", method = RequestMethod.GET)
    public String messages(HttpSession session, Model model){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/forbidden";
        }
        return "messages";
    }

}
