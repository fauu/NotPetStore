package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.TestUtil;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AddSnippetsIntegrationTests extends SnippetsIntegrationTests {

  private static final
  MockHttpServletRequestBuilder ADD_SNIPPET_REQUEST_EMPTY_CONTENT;

  private static final
  MockHttpServletRequestBuilder ADD_SNIPPET_REQUEST_VALID;

  private static final String DUMMY_TITLE = "Title";

  private static final String DUMMY_CONTENT = TestUtil.generateDummyString(140);

  private static final Snippet.SyntaxHighlighting DUMMY_SYNTAX_HIGHLIGHTING =
      Snippet.SyntaxHighlighting.NONE;

  private static final String DUMMY_OWNER_PASSWORD = "Password";

  private static final Snippet.Visibility DUMMY_VISIBILITY =
      Snippet.Visibility.PUBLIC;


  static {
    ADD_SNIPPET_REQUEST_EMPTY_CONTENT =
        post("/").param("title", DUMMY_TITLE)
                 .param("content", "")
                 .param("syntaxHighlighting", DUMMY_SYNTAX_HIGHLIGHTING.name())
                 .param("ownerPassword", DUMMY_OWNER_PASSWORD)
                 .param("visibility", DUMMY_VISIBILITY.name());

    ADD_SNIPPET_REQUEST_VALID =
        post("/").param("title", DUMMY_TITLE)
                 .param("content", DUMMY_CONTENT)
                 .param("syntaxHighlighting", DUMMY_SYNTAX_HIGHLIGHTING.name())
                 .param("expirationMoment",
                     SnippetForm.ExpirationMoment.IN_TEN_MINUTES.name())
                 .param("ownerPassword", DUMMY_OWNER_PASSWORD)
                 .param("visibility", DUMMY_VISIBILITY.name());
  }

  @Value("${snippet.idSymbols}")
  private char[] snippetIdSymbols;

  @Value("${snippet.idLength}")
  private int snippetIdLength;

  @Test
  public void add_ShouldTryToRenderAddSnippetPage() throws Exception {
    mockMvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void doAdd_EmptyContent_ShouldTryToRenderAddSnippetPage()
      throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_EMPTY_CONTENT)
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void doAdd_EmptyContent_ShouldHaveValidationErrorForContent()
      throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_EMPTY_CONTENT)
           .andExpect(model().attributeHasFieldErrors("snippetForm",
                                                      "content"));
  }

  @Test
  public void INCOMPLETE_doAdd_ShouldRedirectToAddedSnippetViewPage()
      throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_VALID)
           .andExpect(status().is3xxRedirection());
  }

  @Test
  public void doAdd_ShouldStoreSnippet() throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_VALID);

    Snippet snippet = snippetRepository.findAll().get(0);

    assertThat(snippet.getId().length(),
        is(snippetIdLength));

    Set<Character> snippetIdSymbolsAsSet = new HashSet<>();
    for (char c : snippetIdSymbols) {
      snippetIdSymbolsAsSet.add(c);
    }
    assertThat(snippet.getId()
                      .chars()
                      .allMatch(c -> snippetIdSymbolsAsSet.contains((char) c)),
        is(true));

    assertThat(snippet.getTitle(),
        is(DUMMY_TITLE));

    assertThat(snippet.getContent(),
        is(DUMMY_CONTENT));

    assertThat(snippet.getSyntaxHighlighting(),
        is(DUMMY_SYNTAX_HIGHLIGHTING));

    assertThat(snippet.getExpiresAt()
            .minusMinutes(10)
            .isEqual(snippet.getCreatedAt()),
        is(true));

    assertThat(bCryptPasswordEncoder.matches(DUMMY_OWNER_PASSWORD,
            snippet.getOwnerPassword()),
        is(true));

    assertThat(snippet.getVisibility(),
        is(DUMMY_VISIBILITY));

    LocalDateTime now = LocalDateTime.now();
    assertThat(snippet.getCreatedAt()
                      .isAfter(now.minusMinutes(1)),
        is(true));
    assertThat(snippet.getCreatedAt()
                      .isBefore(now),
        is(true));

    assertThat(snippet.getViewCount(),
        is(0));
  }

}
