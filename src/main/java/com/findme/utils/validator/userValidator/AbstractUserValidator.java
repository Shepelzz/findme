package com.findme.utils.validator.userValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.params.UserValidatorParams;

public abstract class AbstractUserValidator {
    private AbstractUserValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractUserValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(UserValidatorParams params) throws BadRequestException {
        checkParam(params);
        if(nextChainValidator!= null)
            nextChainValidator.check(params);
    }

    protected abstract void checkParam(UserValidatorParams params) throws BadRequestException;
}
