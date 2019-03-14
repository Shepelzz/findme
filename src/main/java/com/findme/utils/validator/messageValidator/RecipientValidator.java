package com.findme.utils.validator.messageValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.params.MessageValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class RecipientValidator extends AbstractMessageValidator{
    @Override
    protected void checkParam(MessageValidatorParams params) throws BadRequestException {
        log.info("Message recipient relationship status ["+params.getRelationshipStatus()+"] validation");

    }
}
