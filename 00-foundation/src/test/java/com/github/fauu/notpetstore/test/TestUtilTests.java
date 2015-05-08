package com.github.fauu.notpetstore.test;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestUtilTests {

  @Test(expected = IllegalArgumentException.class)
  public void generateDummyString_LengthBelow0_ShouldThrowIllegalArgumentException()
      throws Exception {
    String s = TestUtil.generateDummyString(-1);
  }

  @Test
  public void generateDummyString_ShouldReturnStringOfDesiredLength()
      throws Exception {
    String s = TestUtil.generateDummyString(100);

    assertThat(s.length(), is(100));
  }

  @Test
  public void generateDummyString_ShouldReturnEqualStringsForTwoConsecutiveCalls()
      throws Exception {
    String previousString = TestUtil.generateDummyString(20);
    String string = TestUtil.generateDummyString(20);

    assertThat(string, is(previousString));
  }

}
