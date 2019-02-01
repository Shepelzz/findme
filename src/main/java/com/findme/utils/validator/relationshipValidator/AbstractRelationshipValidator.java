package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.RelationshipValidatorParams;

public abstract class AbstractRelationshipValidator {
    private AbstractRelationshipValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractRelationshipValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(RelationshipValidatorParams params) throws BadRequestException {
        if(checkParam(params))
            return;
        if(nextChainValidator!= null)
            nextChainValidator.check(params);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

    abstract boolean checkParam(RelationshipValidatorParams params) throws BadRequestException ;
}
