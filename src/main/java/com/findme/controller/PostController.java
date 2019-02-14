package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import com.findme.model.PostInfo;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @RequestMapping(path = "posts-list", method = RequestMethod.POST)
    public ResponseEntity<String> getContent1(@ModelAttribute FilterPagePosts filter, @RequestParam String userId, HttpSession session) {
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        List<Post> pl;

        try {
            pl = postService.getPostsByFilter(userId, filter);
        } catch (InternalServerError | NumberFormatException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println(pl.toString());
        return new ResponseEntity<>(pl.toString(), HttpStatus.OK);
    }



    @RequestMapping(path = "/delete-post/{postId}", method = RequestMethod.POST)
    public ResponseEntity<String> deletePost(@PathVariable String postId, HttpSession session){
        if(session.getAttribute("loggedUserId")==null)
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
//        try {
//            postService.delete(Long.valueOf(postId));
//            return new ResponseEntity<>( HttpStatus.OK);
//        } catch (InternalServerError | NumberFormatException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }
}
