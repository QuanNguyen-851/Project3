package com.example.project3.Common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FormatDate {
  public static Date convertLocalDateTimeToDateUsingTimestamp(LocalDateTime dateToConvert) {
    return java.sql.Timestamp.valueOf(dateToConvert);
  }
}
