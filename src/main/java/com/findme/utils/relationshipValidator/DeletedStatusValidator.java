package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

import java.util.Date;

public class DeletedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.FRIENDS;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;

    @Override
    protected void checkParam(RelationshipStatus currentStatus, RelationshipStatus newStatus, Date relDateModified, int friendsCnt, int outgoingReqCnt) {
        if(currentStatus.equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS))
            return;


//        if(newStatus == RelationshipStatus.DELETED) {
//            if(currentStatus != RelationshipStatus.FRIENDS)
//                throw new BadRequestException("DELETED Request can not be processed");
//        }
    }
}
