package com.findme.controller;

import com.findme.exception.InternalServerError;
import com.findme.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CountryController {
    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(path = "/get-countries-small-list", method = RequestMethod.GET)
    public ResponseEntity<?> getCountryList(HttpSession session, @RequestParam String searchWord){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        try {
            return new ResponseEntity<>(countryService.getCountriesByWord(searchWord), HttpStatus.OK);
        } catch (InternalServerError ise){
            return new ResponseEntity<>(ise.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
