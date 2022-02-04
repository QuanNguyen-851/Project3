package com.example.project3.Common;

import java.util.Base64;

public class Token {
  public static String convertHashToString(String text) {
    String token = Base64.getEncoder().encodeToString(text.getBytes());
    return token;
  }
}
