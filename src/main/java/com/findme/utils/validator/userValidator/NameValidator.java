package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class NameValidator extends AbstractUserValidator {
    private static final Pattern namePattern = Pattern.compile("^[\\p{L}]{4,50}+$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException {
        log.info("User name ["+params.getFirstName()+" "+params.getLastName()+"] validation");

        if(!namePattern.matcher(params.getFirstName()).matches()) {
            log.warn("User first name validation fail. First name ["+params.getFirstName()+"] is not valid");
            throw new BadRequestException("User first name validation fail. First name ["+params.getFirstName()+"] is not valid");
        }
        if(!namePattern.matcher(params.getLastName()).matches()) {
            log.warn("User last name validation fail. Last name ["+params.getLastName()+"] is not valid");
            throw new BadRequestException("User last name validation fail. Last name ["+params.getLastName()+"] is not valid");
        }
    }
}
