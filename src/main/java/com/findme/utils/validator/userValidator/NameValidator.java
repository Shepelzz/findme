package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;

import java.util.regex.Pattern;

public class NameValidator extends AbstractUserValidator {
    private static final Pattern namePattern = Pattern.compile("^[\\p{L}]{4,50}+$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException {
        if(!namePattern.matcher(params.getFirstName()).matches())
            throw new BadRequestException("First name is not valid.");
        if(!namePattern.matcher(params.getLastName()).matches())
            throw new BadRequestException("Last name is not valid.");
    }
}
