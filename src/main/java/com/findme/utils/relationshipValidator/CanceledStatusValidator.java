package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

public class CanceledStatusValidator extends AbstractRelationshipValidator {

    @Override
    protected void checkParam(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException {

        if(newStatus == RelationshipStatus.CANCELED) {
            if(oldStatus != RelationshipStatus.REQUESTED)
                throw new BadRequestException("CANCELED Request can not be processed");
        }
    }
}
