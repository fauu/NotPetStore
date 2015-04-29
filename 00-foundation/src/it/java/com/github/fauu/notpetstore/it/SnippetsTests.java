package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class SnippetsTests extends AbstractIntegrationTests {

  private MockMvc mockMvc;

  @Autowired
  private SnippetRepository snippetRepository;

  private MockHttpServletRequestBuilder addSnippetRequestEmptyContent;

  private MockHttpServletRequestBuilder addSnippetRequestValid;

  @Before
  public void setup() {
    mockMvc = webAppContextSetup(context).build();

    addSnippetRequestEmptyContent
        = post("/").param("title", "Title")
                   .param("content", "");

    addSnippetRequestValid
        = post("/").param("title", "Title")
                   .param("content", "Content");

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
  public void doAdd_ValidForm_ShouldRedirectToRoot() throws Exception {
    mockMvc.perform(addSnippetRequestValid)
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/"))
           .andExpect(redirectedUrl("/"));
  }

  @Test
  public void doAdd_ValidForm_ShouldStoreSnippet() throws Exception {
    mockMvc.perform(addSnippetRequestValid);

    Snippet snippet = snippetRepository.findAll().get(0);

    assertThat(snippet.getTitle(), is("Title"));
    assertThat(snippet.getContent(), is("Content"));
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
