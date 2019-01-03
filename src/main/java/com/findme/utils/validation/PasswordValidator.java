package com.findme.utils.validation;

import com.findme.exception.BadRequestException;

import java.util.Map;
import java.util.regex.Pattern;

public class PasswordValidator extends AbstractChainValidator{
    private static final Pattern passwordPattern = Pattern.compile("^(?=\\S+$).{4,50}$");
    @Override
    void checkParam(Map<String, String> objectMap) throws BadRequestException {
        if(!passwordPattern.matcher(objectMap.get("password")).matches())
            throw new BadRequestException("Password is not valid.");
    }
}
