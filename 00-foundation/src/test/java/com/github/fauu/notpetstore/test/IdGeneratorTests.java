package com.github.fauu.notpetstore.test;

import com.github.fauu.notpetstore.util.IdGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class IdGeneratorTests {

  @Test(expected = IllegalArgumentException.class)
  public void generate_LengthBelow3_ShouldThrowIllegalArgumentException()
      throws Exception {
      String id = IdGenerator.generate(2);
  }

  @Test
  public void generate_ShouldReturnStringOfDesiredLength() throws Exception {
    String id = IdGenerator.generate(15);

    assertEquals(15, id.length());
  }

  @Test
  public void generate_ShouldReturnStringWithOnlyAlphanumericCharacters()
      throws Exception {
    String id = IdGenerator.generate(8);

    assertTrue(id.matches("[0-9a-zA-z]+"));
  }

  @Test
  public void generate_ShouldReturnDifferentStringsForTwoConsecutiveCalls()
      throws Exception {
    String previousId = IdGenerator.generate(8);
    String id = IdGenerator.generate(8);

    assertNotEquals(previousId, id);
  }

}
