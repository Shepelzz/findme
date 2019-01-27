package com.findme.utils.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.AbstractChainValidator;

import java.util.Map;
import java.util.regex.Pattern;

public class EmailValidator extends AbstractChainValidator<Map<String,String>> {
    private static final Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    protected void checkParam(Map<String, String> objectMap) throws BadRequestException{
        if(objectMap.get("email").length()>=50)
            throw new BadRequestException("Email larger is larger than 50.");
        if(!emailPattern.matcher(objectMap.get("email")).matches())
            throw new BadRequestException("Email is not valid.");
    }
}
