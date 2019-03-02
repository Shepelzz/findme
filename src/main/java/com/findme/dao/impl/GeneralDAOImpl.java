package com.findme.dao.impl;

import com.findme.dao.GeneralDAO;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.GeneralModel;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Log4j
@Repository
public abstract class GeneralDAOImpl<T extends GeneralModel> implements GeneralDAO<T> {
    private Class<T> clazz;
    private String SQL_DELETE_BY_ID;

    @PersistenceContext
    EntityManager entityManager;

    final void setClazz( Class<T> clazzToSet ){
        this.clazz = clazzToSet;
        this.SQL_DELETE_BY_ID = "DELETE FROM "+clazz.getSimpleName()+" t WHERE t.id = :id";
    }

    @Override
    @Transactional
    public T save(T t) throws InternalServerError {
        try {
            entityManager.persist(t);
            log.info("New "+clazz.getSimpleName()+" saved with id "+t.getId());

            return t;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    @Transactional
    public T update(T t) throws InternalServerError {
        try {
            entityManager.merge(t);
            log.info("Entity "+clazz.getSimpleName()+" with id "+t.getId()+" was updated");

            return t;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws InternalServerError{
        int res;
        try {
            res = entityManager.createQuery(SQL_DELETE_BY_ID).setParameter("id", id).executeUpdate();
            if (res == 0) {
                String errorMessage = "Entity "+clazz.getSimpleName() + " with id: " + id + " was not deleted";
                log.warn(errorMessage);
                throw new InternalServerError(errorMessage);
            } else
                log.info("Entity "+clazz.getSimpleName() + " with id: " + id + " was deleted");
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }


    @Override
    public T findById(Long id) throws InternalServerError{
        try {
           return entityManager.find(clazz, id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
