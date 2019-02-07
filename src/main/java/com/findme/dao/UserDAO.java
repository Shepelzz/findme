package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.entity.User;

import java.util.List;

public interface UserDAO extends GeneralDAO<User> {

    List<User> getFirstUsers() throws InternalServerError;
    User getUserByEmailOrPhone(String email, String phone) throws InternalServerError;
    User getUserByAuthorization(String email, String password) throws InternalServerError;
}
