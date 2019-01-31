package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

public abstract class AbstractRelationshipValidator {
    private AbstractRelationshipValidator nextChainValidator;
    private boolean s = false;

    public void setS(boolean s) {
        this.s = s;
    }

    public void setNextAbstractChainValidator(AbstractRelationshipValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException {
        if(s==false)
            checkParam(oldStatus, newStatus);
        if(nextChainValidator!= null)
            nextChainValidator.check(oldStatus, newStatus);
    }

    protected abstract void checkParam(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException;
}
