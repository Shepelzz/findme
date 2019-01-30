package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

public abstract class AbstractRelationshipValidator {
    private AbstractRelationshipValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractRelationshipValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException {
        checkParam(oldStatus, newStatus);
        if(nextChainValidator!= null)
            nextChainValidator.check(oldStatus, newStatus);
    }

    protected abstract void checkParam(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException;
}
