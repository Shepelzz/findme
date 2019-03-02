package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.UserValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class PhoneValidator extends AbstractUserValidator {
    private static final Pattern phonePattern = Pattern.compile("^\\+\\d{12}$");
    @Override
    protected void checkParam(UserValidatorParams params) throws BadRequestException {
        log.info("User phone validation");

        if(!phonePattern.matcher(params.getPhone()).matches()){
            log.warn("Phone is not valid");
            throw new BadRequestException("Phone is not valid");
        }
    }
}
