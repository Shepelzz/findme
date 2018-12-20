package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.models.User;

public interface UserService{

    User save(User user) throws BadRequestException;
    User update(User user) throws BadRequestException;
    void delete(Long id) throws InternalServerError;
    User findById(Long id) throws BadRequestException;

}
