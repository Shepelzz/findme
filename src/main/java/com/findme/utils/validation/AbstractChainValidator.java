package com.findme.utils.validation;

import com.findme.exception.BadRequestException;

import java.util.Map;

public abstract class AbstractChainValidator {
    private AbstractChainValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractChainValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(Map<String, String> objectMap) throws BadRequestException {
        checkParam(objectMap);
        if(nextChainValidator!= null)
            nextChainValidator.check(objectMap);
    }

    abstract void checkParam(Map<String, String> objectMap) throws BadRequestException;
}
