package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.test.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.matches;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SnippetsTests extends AbstractIntegrationTests {

  @Autowired
  private SnippetRepository snippetRepository;

  private MockHttpServletRequestBuilder addSnippetRequestEmptyContent;

  private MockHttpServletRequestBuilder addSnippetRequestValid;

  @Override
  @Before
  public void setUp() {
    super.setUp();

    addSnippetRequestEmptyContent
        = post("/").param("title", "Title")
                   .param("content", "")
                   .param("visibility", Snippet.Visibility.PUBLIC.name());

    addSnippetRequestValid
        = post("/").param("title", "Title")
                   .param("content", TestUtil.generateDummyString(140))
                   .param("visibility", Snippet.Visibility.PUBLIC.name());

    snippetRepository.deleteAll();
  }

  @Test
  public void add_ShouldTryToRenderAddSnippetPage() throws Exception {
    mockMvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void doAdd_EmptyContent_ShouldTryToRenderAddSnippetPage() throws Exception {
    mockMvc.perform(addSnippetRequestEmptyContent)
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void doAdd_EmptyContent_ShouldHaveValidationErrorForContent()
      throws Exception {
    mockMvc.perform(addSnippetRequestEmptyContent)
           .andExpect(model().attributeHasFieldErrors("snippetForm", "content"));
  }

  @Test
  public void doAdd_ShouldRedirectToRoot() throws Exception {
    mockMvc.perform(addSnippetRequestValid)
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/"))
           .andExpect(redirectedUrl("/"));
  }

  @Test
  public void doAdd_ShouldStoreSnippet() throws Exception {
    mockMvc.perform(addSnippetRequestValid);

    Snippet snippet = snippetRepository.findAll().get(0);

    assertThat(snippet.getId().length(), is(8));
    assertThat(snippet.getId().matches("[0-9a-zA-Z]+"), is(true));
    assertThat(snippet.getTitle(), is("Title"));
    assertThat(snippet.getContent(), is(TestUtil.generateDummyString(140)));
    assertThat(snippet.getVisibility(), is(Snippet.Visibility.PUBLIC));
    assertThat(snippet.getDateTimeAdded().isBefore(LocalDateTime.now()), is(true));
    assertThat(snippet.getNumViews(), is(0));
  }

  @Test
  public void browse_ShouldTryToRenderBrowseSnippetsPage() throws Exception {
    mockMvc.perform(get("/browse"))
           .andExpect(status().isOk())
           .andExpect(view().name("browse"))
           .andExpect(forwardedUrlPattern("/**/browse.*"));
  }

  @Test
  public void browse_ShouldHaveSnippetList() throws Exception {
    Snippet snippet1 = new Snippet();
    snippet1.setTitle("Title 1");
    snippet1.setContent("Content 1");

    Snippet snippet2 = new Snippet();
    snippet2.setTitle("Title 2");
    snippet2.setContent("Content 2");

    snippetRepository.save(snippet1);
    snippetRepository.save(snippet2);

    mockMvc.perform(get("/browse"))
           .andExpect(model().attribute("snippets", hasSize(2)))
           .andExpect(model().attribute("snippets", hasItems(
                 hasProperty("title", is("Title 1")),
                 hasProperty("content", is("Content 2"))
               )));
  }

}
