package com.findme.dao;

import com.findme.models.GeneralModel;

public interface GeneralDAO<T extends GeneralModel> {

    T save(T t);
    T update(T t);
    void delete(Long id);
    T findById(Long id);

}
