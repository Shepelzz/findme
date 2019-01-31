package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

import java.util.Date;

public class CanceledStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.CANCELED;

    @Override
    protected void checkParam(RelationshipStatus currentStatus, RelationshipStatus newStatus, Date relDateModified, int friendsCnt, int outgoingReqCnt) {
        if(currentStatus.equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS))
            return;

//        if(newStatus == RelationshipStatus.CANCELED) {
//            if(currentStatus != RelationshipStatus.REQUESTED)
//                throw new BadRequestException("CANCELED Request can not be processed");
//        }
    }
}
