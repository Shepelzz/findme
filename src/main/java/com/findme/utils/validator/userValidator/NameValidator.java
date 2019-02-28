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
            String msg = "First name is not valid";
            log.warn(msg);
            throw new BadRequestException(msg);
        }
        if(!namePattern.matcher(params.getLastName()).matches()) {
            String msg = "Last name is not valid";
            log.warn(msg);
            throw new BadRequestException(msg);
        }
    }
}
