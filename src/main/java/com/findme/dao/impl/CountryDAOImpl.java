package com.findme.dao.impl;

import com.findme.dao.CountryDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.Country;

import java.util.List;

public class CountryDAOImpl extends GeneralDAOImpl<Country> implements CountryDAO {
    private static final String SQL_GET_COUNTRIES_BY_WORD = "SELECT c FROM Country c WHERE c.name like :searchWord ORDER BY c.name";

    public CountryDAOImpl() {
        setClazz(Country.class);
    }

    @Override
    public List<Country> getCountriesByWord(String searchWord) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_COUNTRIES_BY_WORD, Country.class)
                    .setParameter("searchWord", "%"+ searchWord+"%")
                    .setMaxResults(10)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
