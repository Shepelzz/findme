package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.PostValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class UserPagePostedValidator extends AbstractPostValidator {

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {
        log.info("Post page validation");

        if(!params.getPostInfo().getUserPostedId().equals(params.getPostInfo().getUserPagePostedId()))
            if (params.getRelBtwAuthorAndPagePostedUser() == null || !params.getRelBtwAuthorAndPagePostedUser().getStatus().equals(RelationshipStatus.FRIENDS)) {
                String msg = "Post can be posted only on your own or friends page";
                log.warn(msg);
                throw new BadRequestException(msg);
            }
    }
}
