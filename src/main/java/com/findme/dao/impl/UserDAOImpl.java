package com.findme.dao.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.User;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j
@Repository
public class UserDAOImpl extends GeneralDAOImpl<User> implements UserDAO {
    private static final String SQL_TOP_USERS = "SELECT u FROM User u ORDER BY dateRegistered DESC";
    private static final String SQL_GET_USER_BY_EMAIL_OR_PHONE = "SELECT u FROM User u WHERE email = :email OR phone = :phone";
    private static final String SQL_GET_USER_BY_AUTH = "SELECT u FROM User u WHERE email = :email AND password = :password";

    public UserDAOImpl() {
        setClazz(User.class);
    }

    public List<User> getFirstUsers() throws InternalServerError{
        try {
            return entityManager.createQuery(SQL_TOP_USERS, User.class)
                    .setMaxResults(10)
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User getUserByEmailOrPhone(String email, String phone) throws InternalServerError{
        try {
            User result = null;
            List<User> userList = entityManager.createQuery(SQL_GET_USER_BY_EMAIL_OR_PHONE, User.class)
                    .setParameter("email", email)
                    .setParameter("phone", phone)
                    .getResultList();
            if(userList != null && userList.size() > 0)
                result = userList.get(0);
            return result;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User getUserByAuthorization(String email, String password) throws InternalServerError{
        try {
            User result = null;
            List<User> userList = entityManager.createQuery(SQL_GET_USER_BY_AUTH, User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getResultList();
            if(userList != null && userList.size() > 0)
                result = userList.get(0);
            return result;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
