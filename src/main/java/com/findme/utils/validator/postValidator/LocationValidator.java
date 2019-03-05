package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.PostValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class LocationValidator extends AbstractPostValidator{
    private static final Pattern locationNamePattern = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {
        log.info("Post location ["+params.getPostInfo().getLocation()+"] validation");

        if(!params.getPostInfo().getLocation().equals("") && !locationNamePattern.matcher(params.getPostInfo().getLocation()).matches()) {
            log.warn("Post location validation fail. Location ["+params.getPostInfo().getLocation()+"] is not valid");
            throw new BadRequestException("Post location validation fail. Location ["+params.getPostInfo().getLocation()+"] is not valid");
        }
    }
}
