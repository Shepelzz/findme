package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.Arrays;

@Log4j
public class RequestedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus[] CURRENT_STATUS = {RelationshipStatus.REJECTED, RelationshipStatus.DELETED, RelationshipStatus.CANCELED};
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REQUESTED;


    @Override
    protected void checkParam(RelationshipValidatorParams params) throws BadRequestException {
        log.info("Relationship [REQUESTED] status validation");

        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(Arrays.stream(CURRENT_STATUS).noneMatch(params.getOldStatus()::equals)) {
                String msgStatusCheck = "[CANCELED] Request can not be processed";
                log.warn(msgStatusCheck);
                throw new BadRequestException(msgStatusCheck);
            }
            if(params.getFriendsCnt() >= 100) {
                String msgFriendsCntCheck = "Action cannot be performed. You have exceeded the limit on the number of friends";
                log.warn(msgFriendsCntCheck);
                throw new BadRequestException(msgFriendsCntCheck);
            }
            if(params.getOutgoingReqCnt() >= 10) {
                String msgOutReqCntCheck = "Action cannot be performed. You have exceeded the limit on the number of outgoing requests";
                log.warn(msgOutReqCntCheck);
                throw new BadRequestException(msgOutReqCntCheck);
            }
        }
    }
}
