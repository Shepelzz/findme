package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.PostValidatorParams;

import java.util.regex.Pattern;

public class MessageValidator extends AbstractPostValidator{
    private static final Pattern urlPattern = Pattern.compile("^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$");

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {
        if(params.getPostInfo().getMessage().length() > 200)
            throw new BadRequestException("Message can not be more than 200 symbols.");

        if(!params.getPostInfo().getMessage().equals("")){
            for(String word : params.getPostInfo().getMessage().split(" "))
                if(word.contains("http") || urlPattern.matcher(word).matches())
                    throw new BadRequestException("Message can not contain URLs.");
        }
    }
}
