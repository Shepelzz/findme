package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.PostValidatorParams;

public abstract class AbstractPostValidator {
    private AbstractPostValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractPostValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(PostValidatorParams params) throws BadRequestException {
        checkParam(params);
        if(nextChainValidator!= null)
            nextChainValidator.check(params);
    }

    protected abstract void checkParam(PostValidatorParams params) throws BadRequestException;
}
