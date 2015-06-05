package com.github.fauu.notpetstore.common.backing;

import com.github.fauu.notpetstore.common.ContextAwareTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class StringGeneratorTests extends ContextAwareTests {

  private @Autowired StringGenerator stringGenerator;

  private static final char[] dummySymbols;

  static {
    dummySymbols = new char[] { 'a', 'b', 'c', 'A', 'B', 'C', '1', '2', '3'};
  }

  @Test(expected = IllegalArgumentException.class)
  @SuppressWarnings("unused")
  public void generateString_LengthBelow1_ShouldThrowIllegalArgumentException()
      throws Exception {
      String id = stringGenerator.generateString(dummySymbols, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  @SuppressWarnings("unused")
  public void  generateString_EmptySymbolSet_ShouldThrowIllegalArgumentException()
      throws Exception {
    String id = stringGenerator.generateString(new char[] { }, 3);
  }

  @Test
  public void generate_ShouldReturnStringOfDesiredLength() throws Exception {
    String id = stringGenerator.generateString(dummySymbols, 15);

    assertThat(id.length(), is(15));
  }

  @Test
  public void generate_ShouldReturnStringWithOnlyDesiredCharacters()
      throws Exception {
    String id = stringGenerator.generateString(dummySymbols, 8);

    Set<Character> dummySymbolsAsSet = new HashSet<>();
    for (char c : dummySymbols) {
      dummySymbolsAsSet.add(c);
    }

    if (id.chars().anyMatch(c -> !dummySymbolsAsSet.contains((char) c))) {
      fail();
    }
  }

  @Test
  public void generate_ShouldReturnDistinctStringsForTwoConsecutiveCalls()
      throws Exception {
    String previousId = stringGenerator.generateString(dummySymbols, 8);
    String id = stringGenerator.generateString(dummySymbols, 8);

    assertThat(id, is(not(previousId)));
  }

}
