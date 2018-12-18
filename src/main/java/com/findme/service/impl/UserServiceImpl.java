package com.findme.service.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User save(User user) throws BadRequestException {
        validateUser(user);
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public User update(User user) throws BadRequestException {
        validateUser(user);
        return userDAO.update(user);
    }

    @Override
    public void delete(Long id) {
        userDAO.delete(id);
    }

    @Override
    public User findById(Long id) throws BadRequestException {
        User user = userDAO.findById(id);
        if(user == null)
            throw new BadRequestException("User with id "+id+" was not found");
        return user;
    }

    private void validateUser(User user) throws BadRequestException{
        if(user.getFirstName().equals("") || user.getLastName().equals(""))
            throw new BadRequestException("User name can not be empty");
    }
}
