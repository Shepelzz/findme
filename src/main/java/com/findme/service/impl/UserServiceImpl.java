package com.findme.service.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User save(User user) throws InternalServerError, BadRequestException {
        validateUser(user);
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public User update(User user) throws InternalServerError, BadRequestException {
        validateUser(user);
        return userDAO.update(user);
    }

    @Override
    @Transactional
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

    private void validateUser(User user) throws BadRequestException, InternalServerError{
        //check name
        String[] nameList = {user.getFirstName(), user.getLastName()};
        for(String name : nameList){
            if(name.length() == 0)
                throw new BadRequestException("User name can not be empty");
            if(!name.chars().allMatch(Character::isLetter))
                throw new BadRequestException("User name can contain only text characters");
        }
        //check phone
        Pattern phonePattern = Pattern.compile("\\+\\d{12}");
        Matcher phoneMatcher = phonePattern.matcher(user.getPhone());
        if(!phoneMatcher.matches())
            throw new BadRequestException("Phone number is not valid");
        //check email
        Pattern emailPattern = Pattern.compile("^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(user.getEmail());
        if(!emailMatcher.matches())
            throw new BadRequestException("Email is not valid");
        //check password
        if(user.getPassword().length() < 4)
            throw new BadRequestException("Password must be at least 4 character length");
        //check on exists
        if(userDAO.getUserByEmailOrPhone(user.getEmail(), user.getPhone()) != null)
            throw new BadRequestException("There is already registered user with this email or phone");
    }
}
