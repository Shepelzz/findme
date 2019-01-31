package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;

public class FriendsStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.FRIENDS;

    @Override
    protected boolean checkParam(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) {
        return relationship.getStatus().equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS);



//        if(newStatus == RelationshipStatus.FRIENDS) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("FRIENDS Request can not be processed");
//        }
    }
}
