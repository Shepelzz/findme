package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;

public class RejectedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REJECTED;

    @Override
    protected boolean checkParam(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) {
        return relationship.getStatus().equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS);

//        if(newStatus == RelationshipStatus.REJECTED) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("REJECTED Request can not be processed");
//        }
    }
}
