package com.findme.test;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.FilterPagePosts;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/testajax/api/customer")
public class RestWebController {

    private PostService postService;

    @Autowired
    public RestWebController(PostService postService) {
        this.postService = postService;
    }

    List<Customer> cust = new ArrayList<Customer>();

    @GetMapping(value = "/all")
    public ResponseEntity<?> getResource() {
        return new ResponseEntity<>(cust, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> postCustomer(@ModelAttribute Customer customer) {

        if(customer.getFirstname().equals("gog"))
            return new ResponseEntity<>("not gog!", HttpStatus.BAD_REQUEST);
        cust.add(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping(value = "/get-filtered-posts")
    public ResponseEntity<?> postCustomer(@ModelAttribute FilterPagePosts filter) {
        String userId = "1";

        try {
            return new ResponseEntity<>(postService.getPostsByFilter(userId, filter), HttpStatus.OK);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
