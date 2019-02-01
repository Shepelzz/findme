package com.findme.utils.validator.params;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserValidatorParams {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    public static ParamsBuilder newBuilder() {
        return new UserValidatorParams().new ParamsBuilder();
    }

    public class ParamsBuilder {
        private ParamsBuilder(){}

        public ParamsBuilder setFirstName(String firstName) {
            UserValidatorParams.this.firstName = firstName;
            return this;
        }

        public ParamsBuilder setLastName(String lastName) {
            UserValidatorParams.this.lastName = lastName;
            return this;
        }

        public ParamsBuilder setEmail(String email) {
            UserValidatorParams.this.email = email;
            return this;
        }

        public ParamsBuilder setPhone(String phone) {
            UserValidatorParams.this.phone = phone;
            return this;
        }

        public ParamsBuilder setPassword(String password) {
            UserValidatorParams.this.password = password;
            return this;
        }

        public UserValidatorParams build() {
            return UserValidatorParams.this;
        }
    }
}
