package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

public class RequestedStatusValidator extends AbstractRelationshipValidator {

    @Override
    protected void checkParam(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException {
        if(newStatus == RelationshipStatus.REQUESTED) {
            if(oldStatus != RelationshipStatus.CANCELED && oldStatus != RelationshipStatus.DELETED && oldStatus != RelationshipStatus.REJECTED)
                throw new BadRequestException("REQUESTED Request can not be processed");
        }
    }
}
