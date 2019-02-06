package com.findme.controller;

import com.findme.model.PostInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestPostController {


    @RequestMapping(path = "/save-post", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute PostInfo postInfo){

            return new ResponseEntity<>( HttpStatus.OK);

    }

    @RequestMapping(path = "/save-post", method = RequestMethod.GET)
    public String profile(){
        return "postFormTest";
    }


}
