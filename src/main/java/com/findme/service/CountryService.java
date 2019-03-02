package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.Country;

import java.util.List;

public interface CountryService extends GeneralService<Country> {

    Country save(Country country) throws InternalServerError, BadRequestException;
    Country update(Country country) throws InternalServerError, BadRequestException;
    void delete(Long id) throws InternalServerError, BadRequestException;

    List<Country> getCountriesByWord(String searchWord) throws InternalServerError;
}
