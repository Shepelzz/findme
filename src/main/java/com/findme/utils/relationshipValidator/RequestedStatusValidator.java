package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;

import java.util.Arrays;

public class RequestedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus[] CURRENT_STATUS = {RelationshipStatus.REJECTED, RelationshipStatus.DELETED, RelationshipStatus.CANCELED};
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;


    @Override
    protected boolean checkParam(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) {
        return Arrays.stream(CURRENT_STATUS).anyMatch(relationship.getStatus()::equals) && newStatus.equals(NEW_STATUS);



//        if(newStatus == RelationshipStatus.REQUESTED) {
//            if(currentStatus != RelationshipStatus.CANCELED && currentStatus != RelationshipStatus.DELETED && currentStatus != RelationshipStatus.REJECTED)
//                throw new BadRequestException("REQUESTED Request can not be processed");
//        }
    }
}
