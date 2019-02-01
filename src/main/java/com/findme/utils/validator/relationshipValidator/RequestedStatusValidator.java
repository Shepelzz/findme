package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;

import java.util.Arrays;

public class RequestedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus[] CURRENT_STATUS = {RelationshipStatus.REJECTED, RelationshipStatus.DELETED, RelationshipStatus.CANCELED};
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;


    @Override
    protected boolean checkParam(RelationshipValidatorParams params) throws BadRequestException {
            if(Arrays.stream(CURRENT_STATUS).anyMatch(params.getOldStatus()::equals) && params.getNewStatus().equals(NEW_STATUS)) {
                if(params.getFriendsCnt() >= 100)
                    throw new BadRequestException("Action cannot be performed. You have exceeded the limit on the number of friends.");
                if(params.getOutgoingReqCnt() >= 10)
                    throw new BadRequestException("Action cannot be performed. You have exceeded the limit on the number of outgoing requests.");
                return true;
            }
            return false;

//        if(newStatus == RelationshipStatus.REQUESTED) {
//            if(currentStatus != RelationshipStatus.CANCELED && currentStatus != RelationshipStatus.DELETED && currentStatus != RelationshipStatus.REJECTED)
//                throw new BadRequestException("REQUESTED Request can not be processed");
//        }
    }
}
