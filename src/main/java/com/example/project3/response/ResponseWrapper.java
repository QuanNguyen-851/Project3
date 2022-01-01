package com.example.project3.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


//@Builder
public class ResponseWrapper {
    private String responseCode;
    private String responseMessage;
    private Object responseData;
        public ResponseWrapper(EnumResponse response, Object data) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = response.getResponseMessage();
        this.responseData = data;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }
}
