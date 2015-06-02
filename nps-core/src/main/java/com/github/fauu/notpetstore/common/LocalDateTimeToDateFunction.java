package com.github.fauu.notpetstore.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

// TODO: Revisit
public class LocalDateTimeToDateFunction {

  public static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

}
