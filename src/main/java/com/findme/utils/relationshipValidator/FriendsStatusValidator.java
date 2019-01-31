package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

import java.util.Date;

public class FriendsStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.FRIENDS;

    @Override
    protected void checkParam(RelationshipStatus currentStatus, RelationshipStatus newStatus, Date relDateModified, int friendsCnt, int outgoingReqCnt) {
        if(currentStatus.equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS))
            return;


//        if(newStatus == RelationshipStatus.FRIENDS) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("FRIENDS Request can not be processed");
//        }
    }
}
