package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;

public class DeletedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.FRIENDS;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;

    @Override
    protected boolean checkParam(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) {
        return relationship.getStatus().equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS);


//        if(newStatus == RelationshipStatus.DELETED) {
//            if(currentStatus != RelationshipStatus.FRIENDS)
//                throw new BadRequestException("DELETED Request can not be processed");
//        }
    }
}
