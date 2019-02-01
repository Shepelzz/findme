package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;

import java.util.regex.Pattern;

public class PasswordValidator extends AbstractUserValidator {
    private static final Pattern passwordPattern = Pattern.compile("^(?=\\S+$).{4,50}$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException {
        if(!passwordPattern.matcher(params.getPassword()).matches())
            throw new BadRequestException("Password is not valid.");
    }
}
