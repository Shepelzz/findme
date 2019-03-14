package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.params.UserValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class PasswordValidator extends AbstractUserValidator {
    private static final Pattern passwordPattern = Pattern.compile("^(?=\\S+$).{4,50}$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException {
        log.info("User password ["+params.getPassword()+"] validation");

        if(!passwordPattern.matcher(params.getPassword()).matches()) {
            log.warn("User password validation fail. Password ["+params.getPassword()+"] is not valid");
            throw new BadRequestException("User password validation fail. Password ["+params.getPassword()+"] is not valid");
        }
    }
}
