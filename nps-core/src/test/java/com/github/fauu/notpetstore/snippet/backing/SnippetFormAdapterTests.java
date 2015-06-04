package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.ContextAwareTests;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SnippetFormAdapterTests extends ContextAwareTests {

  private static final SnippetForm DUMMY_SNIPPET_FORM;

  private @Autowired SnippetFormAdapter snippetFormAdapter;

  private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

  static {
    DUMMY_SNIPPET_FORM = new SnippetForm();

    DUMMY_SNIPPET_FORM
        .setTitle("title");

    DUMMY_SNIPPET_FORM
        .setContent("content");

    DUMMY_SNIPPET_FORM
        .setSyntaxHighlighting(Snippet.SyntaxHighlighting.JAVA);

    DUMMY_SNIPPET_FORM
        .setExpirationMoment(SnippetForm.ExpirationMoment.IN_TEN_MINUTES);

    DUMMY_SNIPPET_FORM
        .setOwnerPassword("password");

    DUMMY_SNIPPET_FORM
        .setVisibility(Snippet.Visibility.UNLISTED);
  }

  @Test
  public void createSnippetFromSnippetForm_ReturnsSnippetObjectWithProperlyPopulatedFields()
      throws Exception {
    Snippet snippet =
        snippetFormAdapter.createSnippetFromSnippetForm(DUMMY_SNIPPET_FORM);

    assertThat(snippet.getId(),
        is(notNullValue()));

    assertThat(snippet.getTitle(),
        is("title"));

    assertThat(snippet.getContent(),
        is("content"));

    assertThat(snippet.getSyntaxHighlighting(),
        is(Snippet.SyntaxHighlighting.JAVA));

    assertThat(snippet.getDateTimeExpires()
                      .minusMinutes(10)
                      .truncatedTo(ChronoUnit.MINUTES)
                      .isEqual(LocalDateTime.now()),
        is(true));

    assertThat(bCryptPasswordEncoder.matches("password",
            snippet.getOwnerPassword()),
        is(true));

    assertThat(snippet.getVisibility(),
        is(Snippet.Visibility.UNLISTED));

    assertThat(snippet.getDateTimeAdded()
                      .truncatedTo(ChronoUnit.MINUTES)
                      .isEqual(LocalDateTime.now()),
        is(true));

    assertThat(snippet.getNumViews(),
        is(0));

    assertThat(snippet.isDeleted(),
        is(false));
  }

}
