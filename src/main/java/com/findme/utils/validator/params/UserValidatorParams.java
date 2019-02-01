package com.findme.utils.validator.params;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class UserValidatorParams {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

}
