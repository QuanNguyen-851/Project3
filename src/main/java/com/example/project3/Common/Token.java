package com.example.project3.Common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Token {
  public static String convertHashToString(String text) {
    String token = Base64.getEncoder().encodeToString(text.getBytes());
//    MessageDigest md = null;
//    try {
//      md = MessageDigest.getInstance("MD5");
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//    }
//    byte[] hashInBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
//    StringBuilder sb = new StringBuilder();
//    for (byte b : hashInBytes) {
//      sb.append(String.format("%02x", b));
//    }
//    return sb.toString();
    return token;
  }
}
