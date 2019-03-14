package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.params.RelationshipValidatorParams;

public abstract class AbstractRelationshipValidator {
    private AbstractRelationshipValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractRelationshipValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(RelationshipValidatorParams params) throws BadRequestException {
        checkParam(params);
        if(nextChainValidator!= null)
            nextChainValidator.check(params);
    }

    abstract void checkParam(RelationshipValidatorParams params) throws BadRequestException ;
}
