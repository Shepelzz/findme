package com.findme.service.validation;

public enum CheckPatterns {
    EMAIL_PATTERN("^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,}).{4,50}$"),
    PHONE_PATTERN("\\+\\d{12}"),
    PASSWORD_PATTERN("^(?=\\S+$).{4,50}$"),
    NAME_PATTERN("^[\\p{L}]{4,50}+$");

    private String value;

    CheckPatterns(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
