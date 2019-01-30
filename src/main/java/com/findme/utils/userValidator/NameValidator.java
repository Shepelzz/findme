package com.findme.utils.userValidator;

import com.findme.exception.BadRequestException;

import java.util.Map;
import java.util.regex.Pattern;

public class NameValidator extends AbstractUserValidator {
    private static final Pattern namePattern = Pattern.compile("^[\\p{L}]{4,50}+$");
    @Override
    protected void checkParam(Map<String, String> objectMap) throws BadRequestException {
        if(!namePattern.matcher(objectMap.get("firstName")).matches())
            throw new BadRequestException("First name is not valid.");
        if(!namePattern.matcher(objectMap.get("lastName")).matches())
            throw new BadRequestException("Last name is not valid.");
    }
}
