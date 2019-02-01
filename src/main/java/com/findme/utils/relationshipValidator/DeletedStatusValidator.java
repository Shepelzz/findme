package com.findme.utils.relationshipValidator;

import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DeletedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.FRIENDS;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;

    @Override
    protected boolean checkParam(Relationship relationship, RelationshipStatus newStatus, int friendsCnt, int outgoingReqCnt) {
        return relationship.getStatus().equals(CURRENT_STATUS) && newStatus.equals(NEW_STATUS)
                && TimeUnit.DAYS.convert(new Date().getTime() - relationship.getDateModified().getTime(), TimeUnit.MILLISECONDS) > 3;


//        if(newStatus == RelationshipStatus.DELETED) {
//            if(currentStatus != RelationshipStatus.FRIENDS)
//                throw new BadRequestException("DELETED Request can not be processed");
//        }
    }
}
