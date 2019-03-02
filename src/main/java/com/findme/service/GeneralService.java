package com.findme.service;

import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.GeneralModel;

public interface GeneralService<T extends GeneralModel> {

    T findById(Long id) throws InternalServerError, NotFoundException;

}
