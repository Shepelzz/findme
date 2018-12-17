package com.findme.dao.impl;

import com.findme.dao.UserDAO;
import com.findme.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends GeneralDAOImpl<User> implements UserDAO {

    public UserDAOImpl() {
        setClazz(User.class);
    }

}
