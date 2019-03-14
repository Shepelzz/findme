package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.params.RelationshipValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class FriendsStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.REQUESTED;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.FRIENDS;

    @Override
    protected void checkParam(RelationshipValidatorParams params) throws BadRequestException{
        log.info("Relationship [FRIENDS] from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"] status validation");

        if(params.getNewStatus().equals(NEW_STATUS)) {
            if(params.getOldStatus() != CURRENT_STATUS){
                log.warn("Relationship validation fail. [FRIENDS] Request can not be processed from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"]");
                throw new BadRequestException("Relationship validation fail. [FRIENDS] Request can not be processed from user ["+params.getUserFromId()+"] to user ["+params.getUserToId()+"]");
            }
        }
    }
}
