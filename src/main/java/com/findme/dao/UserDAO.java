package com.findme.dao;

import com.findme.models.User;

import java.util.List;

public interface UserDAO extends GeneralDAO<User> {

    public List<User> getFirstUsers();

}
