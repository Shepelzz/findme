package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.PostValidatorParams;

public class UserTaggedValidator extends AbstractPostValidator {

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {

        if(params.getUsersTagged() != null && params.getUsersTagged().size()!=params.getPostInfo().getUsersTaggedIds().size())
            throw new BadRequestException("Tagged users data is not valid.");
    }
}
