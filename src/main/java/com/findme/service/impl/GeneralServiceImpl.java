package com.findme.service.impl;

import com.findme.dao.GeneralDAO;
import com.findme.dao.impl.GeneralDAOImpl;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.GeneralModel;
import com.findme.service.GeneralService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Log4j
@Service
public abstract class GeneralServiceImpl<T extends GeneralModel> implements GeneralService<T>{
    private Class<T> clazz;
    private GeneralDAO<T> generalDAO;

    public GeneralServiceImpl(GeneralDAO<T> generalDAO) {
        this.generalDAO = generalDAO;
    }

    final void setClazz(Class<T> clazzToSet ){
        this.clazz = clazzToSet;
    }

    @Override
    public T findById(Long id) throws InternalServerError, NotFoundException{
        T t = generalDAO.findById(id);
        if(t == null) {
            String msg = "Entity "+clazz.getSimpleName()+" with id " + id + " was not found";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        return t;
    }
}
