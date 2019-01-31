package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;

public class CanceledStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.CANCELED;

    @Override
    boolean checkParam(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) {
        return relationship.getStatus().equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS);


//        if(newStatus == RelationshipStatus.CANCELED) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("CANCELED Request can not be processed");
//        }
    }
}
