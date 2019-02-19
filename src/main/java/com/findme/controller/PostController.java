package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import com.findme.model.PostInfo;
import com.findme.service.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //TODO
    @RequestMapping(path = "/posts-list", method = RequestMethod.POST)
    public ResponseEntity<?> getContent1(@ModelAttribute FilterPagePosts filter, @RequestParam String userId, HttpSession session) {
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);

        if(filter.isFriendsPosts())
            return new ResponseEntity<>("hui", HttpStatus.BAD_REQUEST);

        Post post;
        try {
//            list = postService.getPostsByFilter(userId, filter);
            post = postService.findById(1L);
        } catch (Exception e){
            return new ResponseEntity<>("errorororor", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(post, HttpStatus.OK);


//        try {
//            return new ResponseEntity<>(postService.getPostsByFilter(userId, filter).toString(), HttpStatus.OK);
//        } catch (BadRequestException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (InternalServerError e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
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

    @RequestMapping(path = "/feed", method = RequestMethod.GET)
    public String newsFeed(Model model, HttpSession session, @RequestParam(value = "maxResult", defaultValue = "10") int maxResult){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            model.addAttribute("error", new BadRequestException("You are not logged in to see this information."));
            return "errors/forbidden";
        }

        try {
            model.addAttribute("newsList", postService.getNewsList(Long.valueOf(loggedUserId), maxResult));
        } catch (InternalServerError ise){
            model.addAttribute("error", ise);
            return "errors/internalServerError";
        }
        return "feed";
    }



}
