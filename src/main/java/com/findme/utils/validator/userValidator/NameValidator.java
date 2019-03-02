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
        log.info("User name validation");

        if(!namePattern.matcher(params.getFirstName()).matches()) {
            log.warn("First name is not valid");
            throw new BadRequestException("First name is not valid");
        }
        if(!namePattern.matcher(params.getLastName()).matches()) {
            log.warn("Last name is not valid");
            throw new BadRequestException("Last name is not valid");
        }
    }
}
