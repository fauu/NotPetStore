package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.ContextAwareTests;
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

  private static final SnippetForm dummySnippetForm;

  private @Autowired SnippetFormAdapter snippetFormAdapter;

  private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

  static {
    dummySnippetForm = new SnippetForm();

    dummySnippetForm
        .setTitle("title");

    dummySnippetForm
        .setContent("content");

    dummySnippetForm
        .setSyntaxHighlighting(Snippet.SyntaxHighlighting.JAVA);

    dummySnippetForm
        .setExpirationMoment(SnippetForm.ExpirationMoment.IN_TEN_MINUTES);

    dummySnippetForm
        .setOwnerPassword("password");

    dummySnippetForm
        .setVisibility(Snippet.Visibility.UNLISTED);
  }

  @Test
  public void createSnippetFromSnippetForm_ReturnsSnippetObjectWithProperlyPopulatedFields()
      throws Exception {
    Snippet snippet =
        snippetFormAdapter.createSnippetFromSnippetForm(dummySnippetForm);

    assertThat(snippet.getId(),
        is(notNullValue()));

    assertThat(snippet.getTitle(),
        is("title"));

    assertThat(snippet.getContent(),
        is("content"));

    assertThat(snippet.getSyntaxHighlighting(),
        is(Snippet.SyntaxHighlighting.JAVA));

    assertThat(snippet.getExpiresAt()
                      .minusMinutes(10)
                      .truncatedTo(ChronoUnit.MINUTES)
                      .isEqual(LocalDateTime.now()),
        is(true));

    assertThat(bCryptPasswordEncoder.matches("password",
            snippet.getOwnerPassword()),
        is(true));

    assertThat(snippet.getVisibility(),
        is(Snippet.Visibility.UNLISTED));

    assertThat(snippet.getCreatedAt()
                      .truncatedTo(ChronoUnit.MINUTES)
                      .isEqual(LocalDateTime.now()),
        is(true));

    assertThat(snippet.getViewCount(),
        is(0));

    assertThat(snippet.isDeleted(),
        is(false));
  }

}
