package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.model.Country;

import java.util.List;

public interface CountryDAO extends GeneralDAO<Country> {

    List<Country> getCountriesByWord(String searchWord) throws InternalServerError;
}
