package com.github.fauu.notpetstore.util;


import java.util.Random;

public class IdGenerator {

  private static final char[] SYMBOLS;

  private static final Random RANDOM;

  static {
    StringBuilder symbolsBuilder = new StringBuilder();

    for (char ch = '0'; ch <= '9'; ch++) {
      symbolsBuilder.append(ch);
    }

    for (char ch = 'a'; ch <= 'z'; ch++) {
      symbolsBuilder.append(ch);
    }

    for (char ch = 'A'; ch <= 'Z'; ch++) {
      symbolsBuilder.append(ch);
    }

    SYMBOLS = symbolsBuilder.toString().toCharArray();

    RANDOM = new Random();
  }

  public static String generate(int length) {
    if (length < 3) {
      throw new IllegalArgumentException("Length should be at least 3");
    }

    StringBuilder idBuilder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      idBuilder.append(SYMBOLS[RANDOM.nextInt(SYMBOLS.length)]);
    }

    return idBuilder.toString();
  }

}
