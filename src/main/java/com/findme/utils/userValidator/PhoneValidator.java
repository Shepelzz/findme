package com.findme.utils.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.AbstractChainValidator;

import java.util.Map;
import java.util.regex.Pattern;

public class PhoneValidator extends AbstractChainValidator<Map<String,String>> {
    private static final Pattern phonePattern = Pattern.compile("^\\+\\d{12}$");
    @Override
    protected void checkParam(Map<String, String> objectMap) throws BadRequestException {
        if(!phonePattern.matcher(objectMap.get("phone")).matches())
            throw new BadRequestException("Phone is not valid.");
        System.out.println("Phone is ok.");
    }
}
