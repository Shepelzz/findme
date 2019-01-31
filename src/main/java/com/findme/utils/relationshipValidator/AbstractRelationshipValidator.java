package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;

import java.util.Date;

public abstract class AbstractRelationshipValidator {
    private AbstractRelationshipValidator nextChainValidator;

    public void setNextAbstractChainValidator(AbstractRelationshipValidator nextChainValidator) {
        this.nextChainValidator = nextChainValidator;
    }

    public void check(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) throws BadRequestException {
        if(checkParam(relationship, newStatus, friendsCnt, outgoingReqCnt))
            return;
        if(nextChainValidator!= null)
            nextChainValidator.check(relationship, newStatus, friendsCnt, outgoingReqCnt);
        else
            throw new BadRequestException("Action cannot be performed to this user.");
    }

    abstract boolean checkParam(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) throws BadRequestException ;
}
