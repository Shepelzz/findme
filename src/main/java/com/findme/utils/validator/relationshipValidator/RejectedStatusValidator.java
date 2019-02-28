package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class RejectedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.REJECTED;

    @Override
    protected void checkParam(RelationshipValidatorParams params) throws BadRequestException{
        log.info("Relationship [REJECTED] status validation");

        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(params.getOldStatus() != CURRENT_STATUS) {
                String msgStatusCheck = "[REJECTED] Request can not be processed";
                log.warn(msgStatusCheck);
                throw new BadRequestException(msgStatusCheck);
            }
        }
    }
}
