package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Log4j
public class DeletedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.FRIENDS;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;

    @Override
    protected void checkParam(RelationshipValidatorParams params) throws BadRequestException{
        log.info("Relationship [DELETED] status validation");

        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(params.getOldStatus() != CURRENT_STATUS){
                String msgStatusCheck = "[DELETED] Request can not be processed";
                log.warn(msgStatusCheck);
                throw new BadRequestException(msgStatusCheck);
            }
            if(TimeUnit.DAYS.convert(new Date().getTime() - params.getRelationshipDateModified().getTime(), TimeUnit.MILLISECONDS) <= 3) {
                String msgDateCheck = "3 days have not passed since the beginning of the friendship";
                log.warn(msgDateCheck);
                throw new BadRequestException(msgDateCheck);
            }
        }
    }
}
