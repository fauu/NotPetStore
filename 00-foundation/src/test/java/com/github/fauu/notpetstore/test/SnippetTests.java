package com.github.fauu.notpetstore.test;

import com.github.fauu.notpetstore.model.entity.Snippet;
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
  public void filename_TitledSnippet_ShouldBeLowercaseTitleWithDotTxtExtension()
      throws Exception {
    snippet.setTitle("Title");

    assertThat(snippet.getFilename(), is("title.txt"));
  }

  @Test
  public void filename_TitledSnippet_ShouldReplaceNonAlphanumericCharactersWithUnderscores()
    throws Exception {
    snippet.setTitle("This is a ';:\\|_-@/Title");

    assertThat(snippet.getFilename(), is("this_is_a__________title.txt"));
  }

}