package com.findme.utils.validator.relationshipValidator;

import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;

public class FriendsStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.FRIENDS;

    @Override
    protected boolean checkParam(RelationshipValidatorParams params) {
        return params.getOldStatus().equals(CURRENT_STATUS) && params.getNewStatus().equals(NEW_STATUS);



//        if(newStatus == RelationshipStatus.FRIENDS) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("FRIENDS Request can not be processed");
//        }
    }
}
