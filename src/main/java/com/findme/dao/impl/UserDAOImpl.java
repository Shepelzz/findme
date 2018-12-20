package com.findme.dao.impl;

import com.findme.dao.UserDAO;
import com.findme.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends GeneralDAOImpl<User> implements UserDAO {
    private static final String SQL_TOP_USERS = "SELECT * FROM USERS ORDER BY DATE_REGISTERED DESC";


    public UserDAOImpl() {
        setClazz(User.class);
    }

    public List<User> getFirstUsers(){
        return (List<User>) entityManager.createNativeQuery(SQL_TOP_USERS, User.class)
                .setMaxResults(10)
                .getResultList();
    }
}
