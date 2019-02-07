package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.PostValidatorParams;

public class UserPagePostedValidator extends AbstractPostValidator {

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {

        if(!params.getPostInfo().getUserPostedId().equals(params.getPostInfo().getUserPagePostedId()))
            if (params.getRelBtwAuthorAndPagePostedUser() == null || !params.getRelBtwAuthorAndPagePostedUser().getStatus().equals(RelationshipStatus.FRIENDS))
                throw new BadRequestException("Post can be posted only on your own or friends page");
    }
}
