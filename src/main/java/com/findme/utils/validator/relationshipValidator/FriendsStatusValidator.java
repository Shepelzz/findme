package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;

public class FriendsStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.FRIENDS;

    @Override
    protected void checkParam(RelationshipValidatorParams params) throws BadRequestException{
        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(params.getOldStatus() != CURRENT_STATUS)
                throw new BadRequestException("FRIENDS Request can not be processed");
        }
    }
}
