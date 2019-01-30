package com.findme.utils.userValidator;

import com.findme.exception.BadRequestException;

import java.util.Map;
import java.util.regex.Pattern;

public class PasswordValidator extends AbstractUserValidator {
    private static final Pattern passwordPattern = Pattern.compile("^(?=\\S+$).{4,50}$");
    @Override
    protected void checkParam(Map<String, String> objectMap) throws BadRequestException {
        if(!passwordPattern.matcher(objectMap.get("password")).matches())
            throw new BadRequestException("Password is not valid.");
    }
}
