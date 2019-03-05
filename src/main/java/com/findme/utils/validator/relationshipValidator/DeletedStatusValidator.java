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
        log.info("Relationship [DELETED] from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"] status validation");

        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(params.getOldStatus() != CURRENT_STATUS){
                log.warn("Relationship validation fail. [DELETED] Request can not be processed from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"]");
                throw new BadRequestException("Relationship validation fail. [DELETED] Request can not be processed from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"]");
            }
            if(TimeUnit.DAYS.convert(new Date().getTime() - params.getRelationshipDateModified().getTime(), TimeUnit.MILLISECONDS) <= 3) {
                log.warn("Relationship validation fail. Relationship from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"] is < 3 days");
                throw new BadRequestException("Relationship validation fail. Relationship from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"] is < 3 days");
            }
        }
    }
}
