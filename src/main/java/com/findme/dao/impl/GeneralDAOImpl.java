package com.findme.dao.impl;

import com.findme.dao.GeneralDAO;
import com.findme.models.GeneralModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GeneralDAOImpl<T extends GeneralModel> implements GeneralDAO<T> {
    private Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    final void setClazz( Class<T> clazzToSet ){
        this.clazz = clazzToSet;
    }

    @Override
    public T save(T t){
        entityManager.persist(t);
        return t;
    }

    @Override
    public T update(T t){
        entityManager.merge(t);
        return t;
    }

    @Override
    public void delete(Long id){
        entityManager.remove(entityManager.find(clazz, id));
    }

    @Override
    public T findById(Long id){
        return entityManager.find(clazz, id);
    }
}
