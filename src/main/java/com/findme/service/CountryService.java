package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Country;

import java.util.List;

public interface CountryService {

    Country save(Country country) throws InternalServerError, BadRequestException;
    Country update(Country country) throws InternalServerError, BadRequestException;
    void delete(Long id) throws InternalServerError, BadRequestException;
    Country findById(Long id) throws InternalServerError, NotFoundException;

    List<Country> getCountriesByWord(String searchWord) throws InternalServerError;
}
