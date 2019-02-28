package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class EmailValidator extends AbstractUserValidator {
    private static final Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException{
        log.info("User email validation");

        if(params.getEmail().length()>=50) {
            String msg = "Email larger is larger than 50";
            log.warn(msg);
            throw new BadRequestException(msg);
        }
        if(!emailPattern.matcher(params.getEmail()).matches()) {
            String msg = "Email is not valid";
            log.warn(msg);
            throw new BadRequestException(msg);
        }
    }
}
