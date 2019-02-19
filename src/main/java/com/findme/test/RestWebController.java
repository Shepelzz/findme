package com.findme.test;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/testajax/api/customer")
public class RestWebController {

    //TODO
    //https://www.mkyong.com/spring-boot/spring-boot-ajax-example/
    private List<Customer> cust = new ArrayList<Customer>();

    @GetMapping(value = "/all")
    public Response getResource() {
        return new Response("Done", cust);
    }

    @PostMapping(value = "/save")
    public Response postCustomer(@RequestBody Customer customer) {
        cust.add(customer);

        // Create Response Object
        return new Response("Done", customer);
    }
}
