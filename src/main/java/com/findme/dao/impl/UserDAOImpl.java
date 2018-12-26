package com.findme.dao.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.InternalServerError;
import com.findme.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends GeneralDAOImpl<User> implements UserDAO {
    private static final String SQL_TOP_USERS = "SELECT * FROM USERS ORDER BY DATE_REGISTERED DESC";
    private static final String SQL_GET_USER_BY_EMAIL_OR_PHONE = "SELECT * FROM USERS WHERE EMAIL = : email OR PHONE = : phone";

    public UserDAOImpl() {
        setClazz(User.class);
    }

    public List<User> getFirstUsers() throws InternalServerError{
        try {
            return (List<User>) entityManager.createNativeQuery(SQL_TOP_USERS, User.class)
                    .setMaxResults(10)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User getUserByEmailOrPhone(String email, String phone) throws InternalServerError{
        try {
            //TODO
            return (User) entityManager.createNativeQuery(SQL_GET_USER_BY_EMAIL_OR_PHONE, User.class)
                    .setParameter("email", email)
                    .setParameter("phone", phone)
                    .setMaxResults(1)
                    .getSingleResult();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

}
