package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

public class FriendsStatusValidator extends AbstractRelationshipValidator {

    @Override
    protected void checkParam(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException {

        if(newStatus == RelationshipStatus.FRIENDS) {
            if(oldStatus != RelationshipStatus.REQUESTED)
                throw new BadRequestException("FRIENDS Request can not be processed");
        }
    }
}
