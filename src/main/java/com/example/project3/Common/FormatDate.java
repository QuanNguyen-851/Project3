package com.example.project3.Common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FormatDate {
  public static Date convertToDate(LocalDateTime dateToConvert) {
    return java.sql.Timestamp.valueOf(dateToConvert);
  }
  public static String getThisMonth(){
    var now = LocalDateTime.now();
    return DateTimeFormatter.ofPattern("MM-yyyy").format(now);
  }
}
