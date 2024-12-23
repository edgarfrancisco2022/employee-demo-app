package com.edgarperez.employee_demo_app.dto;

import java.util.Map;

public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
    private Map<String, String> map;

    public ErrorDetails() {
    }

    public ErrorDetails(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorDetails(String errorCode, String errorMessage, Map<String, String> map) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.map = map;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
