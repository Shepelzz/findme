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
    private static final Pattern emailPattern = Pattern.compile("^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,}).{4,50}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern phonePattern = Pattern.compile("^\\+\\d{12}$");
    private static final Pattern passwordPattern = Pattern.compile("^(?=\\S+$).{4,50}$");
    private static final Pattern namePattern = Pattern.compile("^[\\p{L}]{4,50}+$");

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
        checkStringWithPattern(user.getPhone(), phonePattern, "Phone number is not valid.");
        checkEmail(user.getEmail());
        if(userDAO.getUserByEmailOrPhone(user.getEmail(), user.getPhone()) != null)
            throw new BadRequestException("There is already registered user with this email or phone.");

        checkStringWithPattern(user.getPassword(), passwordPattern,"Password is not valid.");
        checkStringWithPattern(user.getFirstName(), namePattern,"First name is not valid.");
        checkStringWithPattern(user.getLastName(), namePattern,"Last name is not valid.");
    }

    private void checkEmail(String email) throws BadRequestException{
        if(email.length()>=50)
            throw new BadRequestException("Email larger is larger than 50.");
        checkStringWithPattern(email, emailPattern, "Email is not valid.");
    }

    private void checkStringWithPattern(String value, Pattern pattern, String errorMessage) throws BadRequestException{
        if(!pattern.matcher(value).matches())
            throw new BadRequestException(errorMessage);
    }
}
