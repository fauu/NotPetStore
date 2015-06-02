package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.TestUtil;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetForm;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static java.util.stream.Collectors.toList;

public class AddSnippetsIntegrationTests extends SnippetsIntegrationTests {

  private static final MockHttpServletRequestBuilder ADD_SNIPPET_REQUEST_EMPTY_CONTENT;

  private static final MockHttpServletRequestBuilder ADD_SNIPPET_REQUEST_VALID;

  static {
    ADD_SNIPPET_REQUEST_EMPTY_CONTENT =
        MockMvcRequestBuilders.post("/").param("title", "Title")
                 .param("content", "")
                 .param("syntaxHighlighting",
                     Snippet.SyntaxHighlighting.NONE.name())
                 .param("ownerPassword", "Password")
                 .param("visibility", Snippet.Visibility.PUBLIC.name());

    ADD_SNIPPET_REQUEST_VALID =
        MockMvcRequestBuilders.post("/").param("title", "Title")
                 .param("content", TestUtil.generateDummyString(140))
                 .param("syntaxHighlighting",
                     Snippet.SyntaxHighlighting.NONE.name())
                 .param("expirationMoment",
                     SnippetForm.ExpirationMoment.TEN_MINUTES.name())
                 .param("ownerPassword", "Password")
                 .param("visibility", Snippet.Visibility.PUBLIC.name());
  }

  @Test
  public void add_ShouldTryToRenderAddSnippetPage() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.view().name("add"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void doAdd_EmptyContent_ShouldTryToRenderAddSnippetPage()
      throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_EMPTY_CONTENT)
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.view().name("add"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void doAdd_EmptyContent_ShouldHaveValidationErrorForContent()
      throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_EMPTY_CONTENT)
           .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("snippetForm", "content"));
  }

  @Test
  public void INCOMPLETE_doAdd_ShouldRedirectToAddedSnippetViewPage() throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_VALID)
           .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
  }

  @Test
  public void doAdd_ShouldStoreSnippet() throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_VALID);

    Snippet snippet = snippetRepository.findAll().collect(toList()).get(0);

    MatcherAssert.assertThat(snippet.getId().length(),
        Matchers.is(8));

    MatcherAssert.assertThat(snippet.getId().matches("[0-9a-zA-Z]+"),
        Matchers.is(true));

    MatcherAssert.assertThat(snippet.getTitle(),
        Matchers.is("Title"));

    MatcherAssert.assertThat(snippet.getContent(),
        Matchers.is(TestUtil.generateDummyString(140)));

    MatcherAssert.assertThat(snippet.getSyntaxHighlighting(),
        Matchers.is(Snippet.SyntaxHighlighting.NONE));

    MatcherAssert.assertThat(snippet.getDateTimeExpires()
            .minusMinutes(10)
            .isEqual(snippet.getDateTimeAdded()),
        Matchers.is(true));

    MatcherAssert.assertThat(bCryptPasswordEncoder.matches("Password", snippet.getOwnerPassword()),
        Matchers.is(true));

    MatcherAssert.assertThat(snippet.getVisibility(),
        Matchers.is(Snippet.Visibility.PUBLIC));

    MatcherAssert.assertThat(snippet.getDateTimeAdded().isBefore(LocalDateTime.now()),
        Matchers.is(true));

    MatcherAssert.assertThat(snippet.getNumViews(),
        Matchers.is(0));
  }

}
