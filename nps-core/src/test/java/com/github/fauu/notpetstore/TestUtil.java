package com.github.fauu.notpetstore;

import java.util.Arrays;

public class TestUtil {

  public static String generateDummyString(int length) {
    if (length < 1) {
      throw new IllegalArgumentException("Length should be at least 1");
    }

    char[] chars = new char[length];

    Arrays.fill(chars, 'a');

    return new String(chars);
  }

}
