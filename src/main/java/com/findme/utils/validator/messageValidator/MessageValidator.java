package com.findme.utils.validator.messageValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.params.MessageValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class MessageValidator extends AbstractMessageValidator{
    @Override
    protected void checkParam(MessageValidatorParams params) throws BadRequestException {
        log.info("Message text ["+params.getMessage().getText()+"] validation");

        if(params.getMessage().getText().length() > 140) {
            log.warn("Message text validation fail. Message ["+params.getMessage().getText()+"] is more than 140 symbols." +
                    " userId: "+params.getMessage().getUserTo().getId());
            throw new BadRequestException("Message text validation fail. Message ["+params.getMessage().getText()+"] is more than 140 symbols." +
                    " userId: "+params.getMessage().getUserTo().getId());
        }

    }
}
