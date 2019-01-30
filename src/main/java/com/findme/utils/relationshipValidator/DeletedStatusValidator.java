package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

public class DeletedStatusValidator extends AbstractRelationshipValidator {

    @Override
    protected void checkParam(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException {

        if(newStatus == RelationshipStatus.DELETED) {
            if(oldStatus != RelationshipStatus.FRIENDS)
                throw new BadRequestException("DELETED Request can not be processed");
        }
    }
}
