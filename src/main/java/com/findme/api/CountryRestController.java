package com.findme.api;

import com.findme.exception.InternalServerError;
import com.findme.service.CountryService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Log4j
@RestController
public class CountryRestController {
    private CountryService countryService;

    @Autowired
    public CountryRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(path = "/get-countries-small-list", method = RequestMethod.GET)
    public ResponseEntity<?> getCountryList(HttpSession session, @RequestParam String searchWord){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(countryService.getCountriesByWord(searchWord), HttpStatus.OK);
    }

}
