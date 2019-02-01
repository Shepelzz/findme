package com.findme.utils.validator.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DeletedStatusValidator extends AbstractRelationshipValidator {
    private final RelationshipStatus CURRENT_STATUS = RelationshipStatus.FRIENDS;
    private final RelationshipStatus NEW_STATUS = RelationshipStatus.DELETED;

    @Override
    protected boolean checkParam(RelationshipValidatorParams params) throws BadRequestException{
        if(params.getOldStatus().equals(CURRENT_STATUS) && params.getNewStatus().equals(NEW_STATUS)) {
            if(TimeUnit.DAYS.convert(new Date().getTime() - params.getRelationshipDateModified().getTime(), TimeUnit.MILLISECONDS) <= 3)
                throw new BadRequestException("Action cannot be performed to this user. 3 days have not passed since the beginning of the friendship.");
            return true;
        }
        return false;


//        if(newStatus == RelationshipStatus.DELETED) {
//            if(currentStatus != RelationshipStatus.FRIENDS)
//                throw new BadRequestException("DELETED Request can not be processed");
//        }
    }
}