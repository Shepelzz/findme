package com.findme.dao.impl;

import com.findme.dao.GeneralDAO;
import com.findme.exception.InternalServerError;
import com.findme.models.GeneralModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class GeneralDAOImpl<T extends GeneralModel> implements GeneralDAO<T> {
    private Class<T> clazz;

    @PersistenceContext
    EntityManager entityManager;

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

    @Transactional
    @Override
    public void delete(Long id) throws InternalServerError{
        final String SQL_DELETE_BY_ID = "DELETE FROM "+clazz.getSimpleName()+" t WHERE t.id = :id";

        int res = entityManager.createQuery(SQL_DELETE_BY_ID).setParameter("id", id).executeUpdate();
        if(res == 0)
            throw new InternalServerError(clazz.getSimpleName()+" with id: "+id+" was not deleted");
    }


    @Override
    public T findById(Long id){
        return entityManager.find(clazz, id);
    }
}
