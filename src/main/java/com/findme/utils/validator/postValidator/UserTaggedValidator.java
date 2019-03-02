package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.PostValidatorParams;
import lombok.extern.log4j.Log4j;

@Log4j
public class UserTaggedValidator extends AbstractPostValidator {

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {
        log.info("Post tagged users validation");

        if(params.getUsersTagged() != null && params.getUsersTagged().size()!=params.getPostInfo().getUsersTaggedIds().size()) {
            log.warn("Tagged users data is not valid");
            throw new BadRequestException("Tagged users data is not valid");
        }
    }
}
