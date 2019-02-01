package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;

import java.util.regex.Pattern;

public class PhoneValidator extends AbstractUserValidator {
    private static final Pattern phonePattern = Pattern.compile("^\\+\\d{12}$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException {
        if(!phonePattern.matcher(params.getPhone()).matches())
            throw new BadRequestException("Phone is not valid.");
        System.out.println("Phone is ok.");
    }
}
