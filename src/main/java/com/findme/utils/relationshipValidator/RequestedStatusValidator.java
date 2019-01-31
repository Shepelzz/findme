package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;

import java.util.Arrays;
import java.util.Date;

public class RequestedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus[] CURRENT_STATUS = {RelationshipStatus.REQUESTED, RelationshipStatus.DELETED, RelationshipStatus.CANCELED};
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;

    @Override
    protected void checkParam(RelationshipStatus currentStatus, RelationshipStatus newStatus, Date relDateModified, int friendsCnt, int outgoingReqCnt) {
        if(Arrays.stream(CURRENT_STATUS).anyMatch(currentStatus::equals) && newStatus.equals(NEW_STATUS))
            return;


//        if(newStatus == RelationshipStatus.REQUESTED) {
//            if(currentStatus != RelationshipStatus.CANCELED && currentStatus != RelationshipStatus.DELETED && currentStatus != RelationshipStatus.REJECTED)
//                throw new BadRequestException("REQUESTED Request can not be processed");
//        }
    }
}
