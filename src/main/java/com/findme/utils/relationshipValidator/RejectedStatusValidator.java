package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

public class RejectedStatusValidator extends AbstractRelationshipValidator {

    @Override
    protected void checkParam(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException {

        if(newStatus == RelationshipStatus.REJECTED) {
            if(oldStatus != RelationshipStatus.REQUESTED)
                throw new BadRequestException("REJECTED Request can not be processed");
        }
    }
}
