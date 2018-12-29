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
        checkStringWithPattern(user.getPhone(), CheckPatterns.PHONE_PATTERN.getPatern(), "Phone number is not valid.");
        checkStringWithPattern(user.getEmail(), CheckPatterns.EMAIL_PATTERN.getPatern(), "Email is not valid.");
        if(userDAO.getUserByEmailOrPhone(user.getEmail(), user.getPhone()) != null)
            throw new BadRequestException("There is already registered user with this email or phone.");

        checkStringWithPattern(user.getPassword(), CheckPatterns.PASSWORD_PATTERN.getPatern(),"Password is not valid.");
        checkStringWithPattern(user.getFirstName(), CheckPatterns.NAME_PATTERN.getPatern(),"First name is not valid.");
        checkStringWithPattern(user.getLastName(), CheckPatterns.NAME_PATTERN.getPatern(),"Last name is not valid.");
    }

    private void checkStringWithPattern(String value, Pattern pattern, String errorMessage) throws BadRequestException{
        if(!pattern.matcher(value).matches())
            throw new BadRequestException(errorMessage);
    }


//    private void validateNewUser(User user) throws BadRequestException, InternalServerError{
//        //check name
//        checkUserName(user.getFirstName(), user.getLastName());
//        //check phone
//        checkUserPhone(user.getPhone());
//        //check email
//        checkUserEmail(user.getEmail());
//        //check password
//        checkUserPassword(user.getPassword());
//        //check on exists
//        checkUserOnExists(user.getEmail(), user.getPhone());
//    }
//
//    private void checkUserName(String firstName, String lastName) throws BadRequestException{
//        if(firstName.length() == 0 || lastName.length() == 0)
//            throw new BadRequestException("User name can not be empty");
//        if(!firstName.chars().allMatch(Character::isLetter) || !lastName.chars().allMatch(Character::isLetter))
//            throw new BadRequestException("User name can contain only text characters");
//    }
//
//    private void checkUserPhone(String phone) throws BadRequestException {
//        Pattern phonePattern = Pattern.compile("\\+\\d{12}");
//        Matcher phoneMatcher = phonePattern.matcher(phone);
//        if(!phoneMatcher.matches())
//            throw new BadRequestException("Phone number is not valid");
//    }
//
//    private void checkUserEmail(String email) throws BadRequestException {
//        Pattern emailPattern = Pattern.compile("^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);
//        Matcher emailMatcher = emailPattern.matcher(email);
//        if(!emailMatcher.matches())
//            throw new BadRequestException("Email is not valid");
//    }
//
//    private void checkUserPassword(String password) throws BadRequestException {
//        if(password.length() < 4)
//            throw new BadRequestException("Password must be at least 4 character length");
//    }
//
//    private void checkUserOnExists(String email, String phone) throws BadRequestException, InternalServerError{
//        if(userDAO.getUserByEmailOrPhone(email, phone) != null)
//            throw new BadRequestException("There is already registered user with this email or phone");
//    }

}
