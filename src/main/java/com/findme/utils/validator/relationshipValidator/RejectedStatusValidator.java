package com.findme.utils.validator.relationshipValidator;

import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;

public class RejectedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REJECTED;

    @Override
    protected boolean checkParam(RelationshipValidatorParams params) {
        return params.getOldStatus().equals(CURRENT_STATUS) && params.getNewStatus().equals(NEW_STATUS);

//        if(newStatus == RelationshipStatus.REJECTED) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("REJECTED Request can not be processed");
//        }
    }
}
