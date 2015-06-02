package com.github.fauu.notpetstore.snippet;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SnippetTests {

  private Snippet snippet = new Snippet();

  @Test
  public void filename_UntitledSnippet_ShouldBeIdWithDotTxtExtensions()
      throws Exception {
    snippet.setId("id");

    assertThat(snippet.getFilename(), is("id.txt"));
  }

  @Test
  public void filename_ShouldBeLowercaseTitleWithDotTxtExtension()
      throws Exception {
    snippet.setTitle("Title");

    assertThat(snippet.getFilename(), is("title.txt"));
  }

  @Test
  public void filename_ShouldReplaceNonAlphanumericCharactersWithUnderscores()
    throws Exception {
    snippet.setTitle("This is a ';:\\|_-@/Title");

    assertThat(snippet.getFilename(), is("this_is_a__________title.txt"));
  }

}
