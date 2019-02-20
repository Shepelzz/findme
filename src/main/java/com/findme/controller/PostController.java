package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.FilterPagePosts;
import com.findme.model.PostInfo;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(path = "/save-post", method = RequestMethod.POST)
    public ResponseEntity<String> savePost(@ModelAttribute PostInfo postInfo, HttpSession session){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        postInfo.setUserPostedId((String) session.getAttribute("loggedUserId"));
        try {
            postService.save(postInfo);
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError | NumberFormatException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = "/get-filtered-posts", method = RequestMethod.POST)
    public ResponseEntity<?> postCustomer(@ModelAttribute FilterPagePosts filter, @RequestParam String userId) {
        try {
            return new ResponseEntity<>(postService.getPostsByFilter(userId, filter), HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/delete-post/{postId}", method = RequestMethod.POST)
    public ResponseEntity<String> deletePost(@PathVariable String postId, HttpSession session){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        //TODO
//        try {
//            postService.delete(Long.valueOf(postId));
//            return new ResponseEntity<>( HttpStatus.OK);
//        } catch (InternalServerError | NumberFormatException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }

    @RequestMapping(path = "/feed", method = RequestMethod.POST)
    public ResponseEntity<?> getNewsFeed(Model model, HttpSession session,
                                         @RequestParam(value = "maxResult", defaultValue = "10") int maxResult,
                                         @RequestParam(value = "currentListPart", defaultValue = "1") int currentListPart){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }

        try {
            return new ResponseEntity<>(postService.getNewsList(Long.valueOf(loggedUserId), maxResult, currentListPart), HttpStatus.OK);
        } catch (InternalServerError ise){
            model.addAttribute("error", ise);
            return new ResponseEntity<>(ise.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
