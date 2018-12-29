package com.findme.service.validation;

import java.util.regex.Pattern;

public enum CheckPatterns {
    EMAIL_PATTERN(Pattern.compile("^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,}).{4,50}$")),
    PHONE_PATTERN(Pattern.compile("^\\+\\d{12}$")),
    PASSWORD_PATTERN(Pattern.compile("^(?=\\S+$).{4,50}$")),
    NAME_PATTERN(Pattern.compile("^[\\p{L}]{4,50}+$"));

    private Pattern pattern;

    CheckPatterns(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPatern(){
        return this.pattern;
    }
}
