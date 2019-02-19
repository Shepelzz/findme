package com.findme.test;

import com.findme.model.Post;
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

    //TODO
    //https://www.mkyong.com/spring-boot/spring-boot-ajax-example/
    private List<Customer> cust = new ArrayList<>();

//    @GetMapping(value = "/all")
//    public Response getResource() {
//        return new Response("Done", cust);
//    }


    @GetMapping(value = "/all")
    public ResponseEntity<Post> getResource() throws Exception{

            return new ResponseEntity<>(postService.findById(1L), HttpStatus.OK);

    }

//    @PostMapping(value = "/save")
//    public Response postCustomer(@RequestBody Customer customer) {
//        cust.add(customer);
//
//        // Create Response Object
//        return new Response("Done", customer);
//    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> postCustomer(@RequestBody Customer customer) {

        if(!customer.getFirstname().equals("gog")){
            cust.add(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>("err", HttpStatus.NOT_FOUND);
    }
}
