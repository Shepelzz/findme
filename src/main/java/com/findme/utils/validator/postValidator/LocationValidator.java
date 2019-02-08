package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.PostValidatorParams;

import java.util.regex.Pattern;

public class LocationValidator extends AbstractPostValidator{
    private static final Pattern locationNamePattern = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {
        if(!params.getPostInfo().getLocation().equals("") && !locationNamePattern.matcher(params.getPostInfo().getLocation()).matches())
            throw new BadRequestException("Location is not valid.");
    }
}
