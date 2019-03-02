package com.findme.service.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.User;
import com.findme.service.GeneralService;
import com.findme.service.UserService;
import com.findme.utils.validator.params.UserValidatorParams;
import com.findme.utils.validator.userValidator.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Log4j
@Service
public class UserServiceImpl extends GeneralServiceImpl<User> implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        super(userDAO);
        setClazz(User.class);
        this.userDAO = userDAO;
    }

    @Override
    public User save(User user) throws InternalServerError, BadRequestException {
        validateUserMainData(user);
        validateEmailAndPhone(user.getEmail(), user.getPhone());

        user.setDateRegistered(new Date());
        return userDAO.save(user);
    }

    @Override
    public User update(User user) throws InternalServerError, BadRequestException{
        User currentUser = userDAO.findById(user.getId());

        //check important fields
        currentUser.setLastName(user.getFirstName());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setPhone(user.getPhone());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        validateUserMainData(currentUser);

        //check other fields
        currentUser.setCountry(user.getCountry());
        currentUser.setCity(user.getCity());
        currentUser.setAge(user.getAge());
        currentUser.setReligion(user.getReligion());
        currentUser.setSchool(user.getSchool());
        currentUser.setUniversity(user.getUniversity());

        return userDAO.update(currentUser);
    }

    @Override
    public void delete(Long id) throws InternalServerError {
        userDAO.delete(id);
    }

    @Override
    @Transactional
    public User login(String email, String password) throws InternalServerError, BadRequestException {
        User user = userDAO.getUserByAuthorization(email, password);
        if(user == null) {
            log.warn("Username or password is incorrect");
            throw new BadRequestException("Username or password is incorrect");
        }
        user.setDateLastActive(new Date());
        userDAO.update(user);
        return user;
    }

    private void validateUserMainData(User user) throws BadRequestException{

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
    }

    private void validateEmailAndPhone(String email, String phone) throws BadRequestException, InternalServerError {
        if(userDAO.getUserByEmailOrPhone(email, phone) != null) {
            log.warn("There is already registered user with this email or phone");
            throw new BadRequestException("There is already registered user with this email or phone");
        }
    }
}
