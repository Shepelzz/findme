package com.findme.utils.validator.postValidator;

import com.findme.exception.BadRequestException;
import com.findme.utils.validator.params.PostValidatorParams;
import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

@Log4j
public class MessageValidator extends AbstractPostValidator{
    private static final Pattern urlPattern = Pattern.compile("^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$");

    @Override
    protected void checkParam(PostValidatorParams params) throws BadRequestException {
        log.info("Post message validation");

        if(params.getPostInfo().getMessage().length() > 200) {
            String msg = "Message can not be more than 200 symbols";
            log.warn(msg);
            throw new BadRequestException(msg);
        }

        if(!params.getPostInfo().getMessage().equals("")){
            for(String word : params.getPostInfo().getMessage().split(" "))
                if(word.contains("http") || urlPattern.matcher(word).matches()) {
                    String msg = "Message can not contain URLs";
                    log.warn(msg);
                    throw new BadRequestException(msg);
                }
        }
    }
}
