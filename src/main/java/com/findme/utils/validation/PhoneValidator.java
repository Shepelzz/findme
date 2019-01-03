package com.findme.utils.validation;

import com.findme.exception.BadRequestException;

import java.util.Map;
import java.util.regex.Pattern;

public class PhoneValidator extends AbstractChainValidator{
    private static final Pattern phonePattern = Pattern.compile("^\\+\\d{12}$");
    @Override
    void checkParam(Map<String, String> objectMap) throws BadRequestException {
        if(!phonePattern.matcher(objectMap.get("phone")).matches())
            throw new BadRequestException("Phone is not valid.");
        System.out.println("Phone is ok.");
    }
}
