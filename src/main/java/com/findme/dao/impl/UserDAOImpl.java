package com.findme.dao.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.InternalServerError;
import com.findme.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImpl extends GeneralDAOImpl<User> implements UserDAO {
    private static final String SQL_TOP_USERS = "SELECT * FROM USERS ORDER BY DATE_REGISTERED DESC";
    private static final String SQL_GET_USER_BY_EMAIL_OR_PHONE = "SELECT u FROM User u WHERE email = :email OR phone = :phone";

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
            User result = null;
            List<User> userList = entityManager.createQuery(SQL_GET_USER_BY_EMAIL_OR_PHONE, User.class)
                    .setParameter("email", email)
                    .setParameter("phone", phone)
                    .getResultList();
            if(userList != null && userList.size() > 0)
                result = userList.get(0);
            return result;
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

}
