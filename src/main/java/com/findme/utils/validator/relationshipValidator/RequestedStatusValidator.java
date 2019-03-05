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
        log.info("Relationship [REQUESTED] from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"] status validation");

        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(Arrays.stream(CURRENT_STATUS).noneMatch(params.getOldStatus()::equals)) {
                log.warn("Relationship validation fail. [REQUESTED] Request can not be processed from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"]");
                throw new BadRequestException("Relationship validation fail. [REQUESTED] Request can not be processed from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"]");
            }
            if(params.getFriendsCnt() >= 100) {
                log.warn("Relationship validation fail. User ["+params.getUserFromId()+"] exceeded the limit on the number of friends");
                throw new BadRequestException("Relationship validation fail. User ["+params.getUserFromId()+"] exceeded the limit on the number of friends");
            }
            if(params.getOutgoingReqCnt() >= 10) {
                log.warn("Relationship validation fail. User ["+params.getUserFromId()+"] exceeded the limit on the number of outgoing requests");
                throw new BadRequestException("Relationship validation fail. User ["+params.getUserFromId()+"] exceeded the limit on the number of outgoing requests");
            }
        }
    }
}
