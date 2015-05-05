package com.github.fauu.notpetstore.test;

import com.github.fauu.notpetstore.util.IdGenerator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class IdGeneratorTests {

  @Test(expected = IllegalArgumentException.class)
  public void generate_LengthBelow3_ShouldThrowIllegalArgumentException()
      throws Exception {
      String id = IdGenerator.generate(2);
  }

  @Test
  public void generate_ShouldReturnStringOfDesiredLength() throws Exception {
    String id = IdGenerator.generate(15);

    assertThat(id.length(), is(15));
  }

  @Test
  public void generate_ShouldReturnStringWithOnlyAlphanumericCharacters()
      throws Exception {
    String id = IdGenerator.generate(8);

    assertThat(id.matches("[0-9a-zA-z]+"), is(true));
  }

  @Test
  public void generate_ShouldReturnUnequalStringsForTwoConsecutiveCalls()
      throws Exception {
    String previousId = IdGenerator.generate(8);
    String id = IdGenerator.generate(8);

    assertThat(id, is(not(previousId)));
  }

}
