package com.company.common;

public final class AppConstants {
    public static final class Regex {
        public static final String REGEX_FULL_NAME = "^[A-Za-z]{2,}(?:\\s[A-Za-z]+)*$";
    }
    public static final class ErrorMessage {
        public static final String NAME_REQUIRED = "name is required";
        public static final String NAME_BLANK = "name can not be blank";
        public static final String NAME_EMPTY = "name can not be empty";

        public static final String EMAIL_REQUIRED = "email is required";
        public static final String PASSWORD_REQUIRED = "password is required";

        public static final String FIELDS_VALIDATION_FAILING_MESSAGE = "One or more fields are failing validation criteria";
    }
    public static final class InfoMessage {

    }
    public static final class Names {
        public static final String SECURITY_REQUIREMENT_BEARER_AUTH = "bearerAuth";
    }
    public static final class Values {
        public static final String SUCCESS_STRING_UPPER = "SUCCESS";
    }
    public static final class API {
        public static final String REGISTRATION_API = "/api/v1/auth/register";
        public static final String LOGIN_API = "/api/v1/auth/login";
    }
    public static final class Literals {
        public static final String EMPTY_STRING = "";
        public static final String SINGLE_BLANK_STRING = " ";
        public static final String COMMA_STRING = ",";
        public static final String PULLSTOP_STRING = ".";
        public static final String PIPE_STRING = "|";
    }
}
