package com.findme.utils.userValidator;

import com.findme.exception.BadRequestException;

import java.util.Map;

public abstract class AbstractUserValidator {
    private AbstractUserValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractUserValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(Map<String, String> params) throws BadRequestException {
        checkParam(params);
        if(nextChainValidator!= null)
            nextChainValidator.check(params);
    }

    protected abstract void checkParam(Map<String, String> params) throws BadRequestException;
}
