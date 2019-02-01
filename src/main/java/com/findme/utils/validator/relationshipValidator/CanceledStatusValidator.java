package com.findme.utils.validator.relationshipValidator;

import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;

public class CanceledStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.CANCELED;

    @Override
    boolean checkParam(RelationshipValidatorParams params) {
        return params.getOldStatus().equals(CURRENT_STATUS) && params.getNewStatus().equals(NEW_STATUS);


//        if(newStatus == RelationshipStatus.CANCELED) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("CANCELED Request can not be processed");
//        }
    }
}
