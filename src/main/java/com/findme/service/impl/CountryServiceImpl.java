package com.findme.service.impl;

import com.findme.dao.CountryDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Country;
import com.findme.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private CountryDAO countryDAO;

    @Autowired
    public CountryServiceImpl(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Override
    public Country save(Country country) throws InternalServerError, BadRequestException {
        return countryDAO.save(country);
    }

    @Override
    public Country update(Country country) throws InternalServerError, BadRequestException {
        return countryDAO.update(country);
    }

    @Override
    public void delete(Long id) throws InternalServerError, BadRequestException {
        countryDAO.delete(id);
    }

    @Override
    public Country findById(Long id) throws InternalServerError, NotFoundException {
        return countryDAO.findById(id);
    }

    @Override
    public List<Country> getCountriesByWord(String searchWord) throws InternalServerError {
        return countryDAO.getCountriesByWord(searchWord);
    }
}
