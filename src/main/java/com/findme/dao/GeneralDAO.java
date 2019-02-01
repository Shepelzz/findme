package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.model.GeneralModel;

public interface GeneralDAO<T extends GeneralModel> {

    T save(T t) throws InternalServerError;
    T update(T t) throws InternalServerError;
    void delete(Long id) throws InternalServerError;
    T findById(Long id) throws InternalServerError;

}
