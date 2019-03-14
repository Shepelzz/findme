package com.findme.utils.validator.messageValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.params.MessageValidatorParams;

public abstract class AbstractMessageValidator {
    private AbstractMessageValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractMessageValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(MessageValidatorParams params) throws BadRequestException {
        checkParam(params);
        if(nextChainValidator!= null)
            nextChainValidator.check(params);
    }

    protected abstract void checkParam(MessageValidatorParams params) throws BadRequestException;
}
