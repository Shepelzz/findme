package com.findme.controller;

import com.findme.dao.PostDAO;
import com.findme.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private UserDAO userDAO;
    private PostDAO postDAO;

    @Autowired
    public HomeController(UserDAO userDAO, PostDAO postDAO) {
        this.userDAO = userDAO;
        this.postDAO = postDAO;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
            String loggedUserId = (String) session.getAttribute("loggedUserId");
            if(loggedUserId != null)
                return "redirect:/user/"+loggedUserId;
            model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
            return "index";
    }

//    @RequestMapping(path = "/test", method = RequestMethod.GET)
//    public String test() throws  Exception{
//        Post post = new Post();
//        post.setUserPosted(userDAO.findById(1L));
//        post.setDatePosted(new Date());
//        post.setLocation("Cyprus");
//        post.setMessage("moremoremoremore olololo tressses");
//        post.setUserPagePosted(userDAO.findById(3L));
//        post.setUsersTagged(new ArrayList<>());
//
//
//        post.addTaggedUser(userDAO.findById(2L));
//        post.addTaggedUser(userDAO.findById(5L));
//
//        System.out.println("1 - ok");
//
//        postDAO.save(post);
//        return "index";
//    }

}