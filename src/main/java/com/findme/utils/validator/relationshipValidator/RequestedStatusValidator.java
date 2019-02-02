package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;

import java.util.Arrays;

public class RequestedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus[] CURRENT_STATUS = {RelationshipStatus.REJECTED, RelationshipStatus.DELETED, RelationshipStatus.CANCELED};
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;


    @Override
    protected void checkParam(RelationshipValidatorParams params) throws BadRequestException {
        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(Arrays.stream(CURRENT_STATUS).noneMatch(params.getOldStatus()::equals))
                throw new BadRequestException("CANCELED Request can not be processed");
            if(params.getFriendsCnt() >= 100)
                throw new BadRequestException("Action cannot be performed. You have exceeded the limit on the number of friends.");
            if(params.getOutgoingReqCnt() >= 10)
                throw new BadRequestException("Action cannot be performed. You have exceeded the limit on the number of outgoing requests.");
        }
    }
}
