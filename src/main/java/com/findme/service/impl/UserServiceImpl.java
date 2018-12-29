package com.findme.service.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
import com.findme.service.validation.CheckPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User save(User user) throws InternalServerError, BadRequestException {
        validateNewUser(user);
        return userDAO.save(user);
    }

    @Override
    public User update(User user) throws InternalServerError, BadRequestException {
        //TODO
        return userDAO.update(user);
    }

    @Override
    public void delete(Long id) throws InternalServerError {
        userDAO.delete(id);
    }

    @Override
    public User findById(Long id) throws InternalServerError, NotFoundException {

        User user = userDAO.findById(id);
        if(user == null)
            throw new NotFoundException("User with id "+id+" was not found");
        return user;
    }

    private void validateNewUser(User user) throws BadRequestException, InternalServerError{
        checkStringWithPattern(user.getPhone(), CheckPatterns.PHONE_PATTERN,"Phone number is not valid.");
        checkStringWithPattern(user.getEmail(), CheckPatterns.EMAIL_PATTERN,"Email is not valid.");
        if(userDAO.getUserByEmailOrPhone(user.getEmail(), user.getPhone()) != null)
            throw new BadRequestException("There is already registered user with this email or phone.");

        checkStringWithPattern(user.getPassword(), CheckPatterns.PASSWORD_PATTERN,"Password is not valid.");
        checkStringWithPattern(user.getFirstName(), CheckPatterns.NAME_PATTERN,"First name is not valid.");
        checkStringWithPattern(user.getLastName(), CheckPatterns.NAME_PATTERN,"Last name is not valid.");
    }

    private void checkStringWithPattern(String value, CheckPatterns pattern, String errorMessage) throws BadRequestException{
        Pattern p = Pattern.compile(pattern.getValue());
        if(!p.matcher(value).matches())
            throw new BadRequestException(errorMessage);
    }
}
