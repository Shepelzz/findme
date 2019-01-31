package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

import java.util.Date;

public abstract class AbstractRelationshipValidator {
    private AbstractRelationshipValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractRelationshipValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(RelationshipStatus currentStatus, RelationshipStatus newStatus, Date relDateModified, int friendsCnt, int outgoingReqCnt) throws BadRequestException {
            checkParam(currentStatus, newStatus, relDateModified, friendsCnt, outgoingReqCnt);
        if(nextChainValidator!= null)
            nextChainValidator.check(currentStatus, newStatus, relDateModified, friendsCnt, outgoingReqCnt);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

    protected abstract void checkParam(RelationshipStatus currentStatus, RelationshipStatus newStatus, Date relDateModified, int friendsCnt, int outgoingReqCnt);
}
