package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.PostValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class UserPagePostedValidator extends AbstractPostValidator {

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {
        log.info("Post page id: ["+params.getPostInfo().getUserPagePostedId()+"] validation");

        if(!params.getPostInfo().getUserPostedId().equals(params.getPostInfo().getUserPagePostedId()))
            if (params.getRelBtwAuthorAndPagePostedUser() == null || !params.getRelBtwAuthorAndPagePostedUser().getStatus().equals(RelationshipStatus.FRIENDS)) {
                log.warn("Post page id validation fail. User id:["+params.getPostInfo().getUserPagePostedId()+"] is not a friend.");
                throw new BadRequestException("Post page id validation fail. User id:["+params.getPostInfo().getUserPagePostedId()+"] is not a friend.");
            }
    }
}
