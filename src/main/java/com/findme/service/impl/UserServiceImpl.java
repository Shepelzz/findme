package com.findme.service.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
import com.findme.types.RelationshipStatus;
import com.findme.utils.AbstractChainValidator;
import com.findme.utils.userValidator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;
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
        validateUserMainData(user);
        user.setDateRegistered(new Date());
        return userDAO.save(user);
    }

    @Override
    public User update(User user) throws InternalServerError, BadRequestException {

        //check non important fields
        user.setCountry(Optional.ofNullable(user.getCountry()).filter(x -> !x.trim().isEmpty()).orElse(null));
        user.setCity(Optional.ofNullable(user.getCity()).filter(x -> !x.trim().isEmpty()).orElse(null));
        user.setAge(Optional.ofNullable(user.getAge()).filter(x -> x > 0).orElse(null));
        user.setRelationshipStatus(Optional.ofNullable(user.getRelationshipStatus()).filter(x -> !x.trim().isEmpty()).orElse(null));
        user.setReligion(Optional.ofNullable(user.getReligion()).filter(x -> !x.trim().isEmpty()).orElse(null));
        user.setSchool(Optional.ofNullable(user.getSchool()).filter(x -> !x.trim().isEmpty()).orElse(null));
        user.setUniversity(Optional.ofNullable(user.getUniversity()).filter(x -> !x.trim().isEmpty()).orElse(null));

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

    @Override
    @Transactional
    public User login(String email, String password) throws InternalServerError, BadRequestException {
        User user = userDAO.getUserByAuthorization(email, password);
        if(user == null)
            throw new BadRequestException("Username or password is incorrect.");
        user.setDateLastActive(new Date());
        userDAO.update(user);
        return user;
    }

    private void validateUserMainData(User user) throws BadRequestException, InternalServerError{

        AbstractChainValidator<Map<String, String>> nameValidator = new NameValidator();
        AbstractChainValidator<Map<String, String>> emailValidator = new EmailValidator();
        AbstractChainValidator<Map<String, String>> phoneValidator = new PhoneValidator();
        AbstractChainValidator<Map<String, String>> passwordValidator = new PasswordValidator();

        nameValidator.setNextAbstractChainValidator(emailValidator);
        emailValidator.setNextAbstractChainValidator(phoneValidator);
        phoneValidator.setNextAbstractChainValidator(passwordValidator);

        nameValidator.check(Collections.unmodifiableMap(new HashMap<String, String>(){{
            put("firstName", user.getFirstName());
            put("lastName", user.getLastName());
            put("email", user.getEmail());
            put("phone", user.getPhone());
            put("password", user.getPassword());
        }}));

        if(userDAO.getUserByEmailOrPhone(user.getEmail(), user.getPhone()) != null)
            throw new BadRequestException("There is already registered user with this email or phone.");
    }
}
