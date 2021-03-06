package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.params.UserValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class EmailValidator extends AbstractUserValidator {
    private static final Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException{
        log.info("User email ["+params.getEmail()+"] validation");

        if(params.getEmail().length()>=50) {
            log.warn("User email validation fail. Email ["+params.getEmail()+"] is larger than 50");
            throw new BadRequestException("User email validation fail. Email ["+params.getEmail()+"] is larger than 50");
        }
        if(!emailPattern.matcher(params.getEmail()).matches()) {
            log.warn("User email validation fail. Email ["+params.getEmail()+"] is not valid");
            throw new BadRequestException("User email validation fail. Email ["+params.getEmail()+"] is not valid");
        }
    }
}
