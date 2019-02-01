package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;

import java.util.regex.Pattern;

public class EmailValidator extends AbstractUserValidator {
    private static final Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException{
        if(params.getEmail().length()>=50)
            throw new BadRequestException("Email larger is larger than 50.");
        if(!emailPattern.matcher(params.getEmail()).matches())
            throw new BadRequestException("Email is not valid.");
    }
}
