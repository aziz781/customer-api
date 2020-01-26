package com.sample.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "message"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseMessage {

    private ResponseStatus status;

    private String message;

    public ResponseMessage(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
