package com.company.common;

import lombok.Getter;

public final class AppConstants {
    public static final class Regex {
        public static final String REGEX_FULL_NAME = "^[A-Za-z]{2,}(?:\\s[A-Za-z]+)*$";
        public static final String REGEX_AADHAR_NO = "^[1-9]\\d{3}-\\d{4}-\\d{4}$";
        public static final String REGEX_PAN = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";
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
        public static final String SYSTEM_UUID = "f72cca88-8e22-42c4-947a-a0b976544d32";
        public static final String SYSTEM_DEFAULT_EMAIL_ADDRESS = "no-reply@company.com";
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
    public static final class EmailTemplate {
        public static final String TRANSFER_REQUEST_SUCCESS_TEMPLATE = "transfer_request_success_template";
        public static final String DEPOSIT_REQUEST_SUCCESS_TEMPLATE = "deposit_request_success_template";
        public static final String WITHDRAW_REQUEST_SUCCESS_TEMPLATE = "transfer_request_success_template";

        public static final String CUSTOMER_CREATED_SUCCESS_TEMPLATE = "customer_created_success_template";
        public static final String BANK_ACCOUNT_CREATED_SUCCESS_TEMPLATE = "bank_account_created_success_template";
    }
}
