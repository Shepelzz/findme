package com.findme.test;

import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
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
}
