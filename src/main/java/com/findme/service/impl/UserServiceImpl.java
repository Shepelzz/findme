package com.findme.service.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.User;
import com.findme.service.UserService;
import com.findme.utils.validator.params.UserValidatorParams;
import com.findme.utils.validator.userValidator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User save(User user) throws InternalServerError, BadRequestException {
        validateUserMainData(user, true);
        user.setDateRegistered(new Date());
        return userDAO.save(user);
    }

    @Override
    public User update(User user) throws InternalServerError, BadRequestException{
        User currentUser = userDAO.findById(user.getId());

        //check important fields
        currentUser.setLastName(Optional.ofNullable(user.getLastName()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getLastName()));
        currentUser.setFirstName(Optional.ofNullable(user.getFirstName()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getFirstName()));
        currentUser.setPhone(Optional.ofNullable(user.getPhone()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getPhone()));
        currentUser.setEmail(Optional.ofNullable(user.getEmail()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getEmail()));
        currentUser.setPassword(Optional.ofNullable(user.getPassword()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getPassword()));
        validateUserMainData(currentUser, false);

        //check other fields
        currentUser.setCountry(Optional.ofNullable(user.getCountry()).orElse(currentUser.getCountry()));
        currentUser.setCity(Optional.ofNullable(user.getCity()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getCity()));
        currentUser.setAge(Optional.ofNullable(user.getAge()).filter(x -> x > 0).orElse(currentUser.getAge()));
        currentUser.setRelationshipStatus(Optional.ofNullable(user.getRelationshipStatus()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getRelationshipStatus()));
        currentUser.setReligion(Optional.ofNullable(user.getReligion()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getReligion()));
        currentUser.setSchool(Optional.ofNullable(user.getSchool()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getSchool()));
        currentUser.setUniversity(Optional.ofNullable(user.getUniversity()).filter(x -> !x.trim().isEmpty()).orElse(currentUser.getUniversity()));

        return userDAO.update(currentUser);
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

    private void validateUserMainData(User user, boolean isNew) throws BadRequestException, InternalServerError{

        AbstractUserValidator nameValidator = new NameValidator();
        AbstractUserValidator emailValidator = new EmailValidator();
        AbstractUserValidator phoneValidator = new PhoneValidator();
        AbstractUserValidator passwordValidator = new PasswordValidator();

        nameValidator.setNextAbstractChainValidator(emailValidator);
        emailValidator.setNextAbstractChainValidator(phoneValidator);
        phoneValidator.setNextAbstractChainValidator(passwordValidator);

        nameValidator.check(UserValidatorParams.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build());

        if(isNew)
            if(userDAO.getUserByEmailOrPhone(user.getEmail(), user.getPhone()) != null)
                throw new BadRequestException("There is already registered user with this email or phone.");
    }
}
