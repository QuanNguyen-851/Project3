package com.example.project3.response;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum EnumResponse {
  SUCCESS("200", "SUCCESS"),
  NOT_FOUND("404", "NOT FOUND"),
  FAIL("400", "SOME_THING_WRONG"),
  EXIST("401", "THIS REQUEST ALREADY EXIST");

  private String responseCode;
  private String getResponseMessage;


  private EnumResponse(String responseCode, String responseMessage) {
    this.responseCode = responseCode;
    this.getResponseMessage = responseMessage;
  }

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public String getResponseMessage() {
    return getResponseMessage;
  }
  public String getCustomMessage(String message){
    return message;
  }

  public void setResponseMessage(String responseMessage) {
    this.getResponseMessage = responseMessage;
  }
}
