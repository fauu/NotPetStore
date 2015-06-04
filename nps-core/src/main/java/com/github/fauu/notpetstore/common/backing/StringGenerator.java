package com.github.fauu.notpetstore.common.backing;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class StringGenerator {

  private static final Random RANDOM;

  static {
    RANDOM = new Random();
  }

  public String generateString(@NonNull char[] symbols, int length) {
    if (symbols.length == 0) {
      throw new IllegalArgumentException("Symbols array cannot be empty");
    }
    if (length < 1) {
      throw new IllegalArgumentException("Length should be at least 1");
    }

    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      stringBuilder.append(symbols[RANDOM.nextInt(symbols.length)]);
    }

    return stringBuilder.toString();
  }

}
