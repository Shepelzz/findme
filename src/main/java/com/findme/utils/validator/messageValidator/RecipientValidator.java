package com.findme.utils.validator.messageValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.params.MessageValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class RecipientValidator extends AbstractMessageValidator{
    @Override
    protected void checkParam(MessageValidatorParams params) throws BadRequestException {
        log.info("Message recipient relationship status from user "+params.getMessage().getUserFrom().getId()+
                " to user "+params.getMessage().getUserTo().getId()+" validation");

        if(params.getRelationship() == null || params.getRelationship().getStatus() != RelationshipStatus.FRIENDS){
            log.warn("Message recipient validation fail. Recipient is not a friend. " +
                    " userId: "+params.getMessage().getUserTo().getId());
            throw new BadRequestException("Message recipient validation fail. Recipient is not a friend. " +
                    " userId: "+params.getMessage().getUserTo().getId());
        }
    }
}
