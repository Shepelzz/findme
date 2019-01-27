package com.findme.service.impl;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
import com.findme.utils.AbstractChainValidator;
import com.findme.utils.userValidator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
        user.setDateRegistered(new Date());
        return userDAO.save(user);
    }

    @Override
    public User update(User user) throws InternalServerError, BadRequestException {
        //TODO check fields
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


    private void validateNewUser(User user) throws BadRequestException, InternalServerError{
        Map<String, String> userParams = new HashMap<>();
            userParams.put("firstName", user.getFirstName());
            userParams.put("lastName", user.getLastName());
            userParams.put("email", user.getEmail());
            userParams.put("phone", user.getPhone());
            userParams.put("password", user.getPassword());

        AbstractChainValidator<Map<String, String>> nameValidator = new NameValidator();
        AbstractChainValidator<Map<String, String>> emailValidator = new EmailValidator();
        AbstractChainValidator<Map<String, String>> phoneValidator = new PhoneValidator();
        AbstractChainValidator<Map<String, String>> passwordValidator = new PasswordValidator();

        nameValidator.setNextAbstractChainValidator(emailValidator);
        emailValidator.setNextAbstractChainValidator(phoneValidator);
        phoneValidator.setNextAbstractChainValidator(passwordValidator);

        nameValidator.check(userParams);

        if(userDAO.getUserByEmailOrPhone(user.getEmail(), user.getPhone()) != null)
            throw new BadRequestException("There is already registered user with this email or phone.");
    }

    private void checkAuthorization(HttpSession session) throws BadRequestException{

        if(session.getAttribute("user") == null)
            throw new BadRequestException("User is not logged in");
    }
}
