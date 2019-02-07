package com.findme.controller;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.PostInfo;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Date;

@Controller
public class TestPostController {

    private PostService postService;
    private UserDAO userDAO;

    @Autowired
    public TestPostController(PostService postService, UserDAO userDAO) {
        this.postService = postService;
        this.userDAO = userDAO;
    }

    @RequestMapping(path = "/save-post", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute PostInfo post){

        PostInfo postInfo = new PostInfo();
        postInfo.setUserPostedId(1L);
        postInfo.setUserPagePostedId(15L);
        postInfo.setMessage("test");
        postInfo.setDatePosted(new Date());
        postInfo.setLocation("somewhere...");
        postInfo.setUsersTaggedIds(Arrays.asList(15L,16L));

        try {
            postService.save(postInfo);
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError | NumberFormatException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/save-post", method = RequestMethod.GET)
    public String profile(){
        return "postFormTest";
    }


}
