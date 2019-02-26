package com.findme.dao.impl;

import com.findme.dao.CountryDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CountryDAOImpl extends GeneralDAOImpl<Country> implements CountryDAO {
    private static final String SQL_GET_COUNTRIES_BY_WORD = "SELECT c FROM Country c WHERE c.name like :searchWord ORDER BY c.name";

    public CountryDAOImpl() {
        setClazz(Country.class);
    }

    @Override
    public List<Country> getCountriesByWord(String searchWord) throws InternalServerError {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Country> criteriaQuery = cb.createQuery(Country.class);
            Root<Country> countryRoot = criteriaQuery.from(Country.class);
            Predicate criteria = cb.conjunction();

            if(searchWord != null && searchWord.length() > 0)
                criteria = cb.and(criteria, cb.like(countryRoot.get("name"), "%"+searchWord+"%"));

            criteriaQuery.select(countryRoot).where(criteria);
            criteriaQuery.orderBy(cb.desc(countryRoot.get("name")));
            return entityManager.createQuery(criteriaQuery).getResultList();
//            return entityManager.createQuery(SQL_GET_COUNTRIES_BY_WORD, Country.class)
//                    .setParameter("searchWord", "%"+ searchWord+"%")
//                    .setMaxResults(10)
//                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
