package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.test.TestUtil;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AddSnippetsIntegrationTests extends SnippetsIntegrationTests {

  private static final MockHttpServletRequestBuilder ADD_SNIPPET_REQUEST_EMPTY_CONTENT;

  private static final MockHttpServletRequestBuilder ADD_SNIPPET_REQUEST_VALID;

  static {
    ADD_SNIPPET_REQUEST_EMPTY_CONTENT =
        post("/").param("title", "Title")
            .param("content", "")
            .param("syntaxHighlighting",
                Snippet.SyntaxHighlighting.NONE.name())
            .param("ownerPassword", "Password")
            .param("visibility", Snippet.Visibility.PUBLIC.name());

    ADD_SNIPPET_REQUEST_VALID =
        post("/").param("title", "Title")
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
           .andExpect(model().attributeHasFieldErrors("snippetForm", "content"));
  }

  @Test
  public void doAdd_ShouldRedirectToBrowseSnippetsUrl() throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_VALID)
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/browse"))
           .andExpect(redirectedUrl("/browse"));
  }

  @Test
  public void doAdd_ShouldStoreSnippet() throws Exception {
    mockMvc.perform(ADD_SNIPPET_REQUEST_VALID);

    Snippet snippet = snippetRepository.findAll().collect(toList()).get(0);

    assertThat(snippet.getId().length(),
        is(8));

    assertThat(snippet.getId().matches("[0-9a-zA-Z]+"),
        is(true));

    assertThat(snippet.getTitle(),
        is("Title"));

    assertThat(snippet.getContent(),
        is(TestUtil.generateDummyString(140)));

    assertThat(snippet.getSyntaxHighlighting(),
        is(Snippet.SyntaxHighlighting.NONE));

    assertThat(snippet.getDateTimeExpires()
                      .minusMinutes(10)
                      .isEqual(snippet.getDateTimeAdded()),
               is(true));

    assertThat(bCryptPasswordEncoder.matches("Password", snippet.getOwnerPassword()),
        is(true));

    assertThat(snippet.getVisibility(),
        is(Snippet.Visibility.PUBLIC));

    assertThat(snippet.getDateTimeAdded().isBefore(LocalDateTime.now()),
        is(true));

    assertThat(snippet.getNumViews(),
        is(0));
  }

}
