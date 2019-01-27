package com.findme.utils;

import com.findme.exception.BadRequestException;

import java.util.Map;

public abstract class AbstractChainValidator<T> {
    private AbstractChainValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractChainValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(T t) throws BadRequestException {
        checkParam(t);
        if(nextChainValidator!= null)
            nextChainValidator.check(t);
    }

    protected abstract void checkParam(T t) throws BadRequestException;
}
