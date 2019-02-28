package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class PasswordValidator extends AbstractUserValidator {
    private static final Pattern passwordPattern = Pattern.compile("^(?=\\S+$).{4,50}$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException {
        log.info("User password validation");

        if(!passwordPattern.matcher(params.getPassword()).matches()) {
            String msg = "Password is not valid";
            log.warn(msg);
            throw new BadRequestException(msg);
        }
    }
}
