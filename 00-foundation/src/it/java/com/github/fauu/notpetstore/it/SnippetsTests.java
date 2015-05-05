package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.test.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SnippetsTests extends AbstractIntegrationTests {

  @Autowired
  private SnippetRepository snippetRepository;

  private MockHttpServletRequestBuilder addSnippetRequestEmptyContent;

  private MockHttpServletRequestBuilder addSnippetRequestValid;

  private List<Snippet> dummySnippets;

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

    dummySnippets = new LinkedList<>();

    Snippet dummySnippet1 = new Snippet();
    dummySnippet1.setId("id1");
    dummySnippet1.setTitle("Title 1");
    dummySnippet1.setContent("Content 1");
    dummySnippet1.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet1.setDateTimeAdded(LocalDateTime.of(2015, Month.SEPTEMBER, 9, 0, 0));
    dummySnippet1.setDeleted(false);

    Snippet dummySnippet2 = new Snippet();
    dummySnippet2.setId("id2");
    dummySnippet2.setTitle("Title 2");
    dummySnippet2.setContent("Content 2");
    dummySnippet2.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet2.setDateTimeAdded(LocalDateTime.of(2015, Month.SEPTEMBER, 10, 0, 0));
    dummySnippet2.setDeleted(false);

    Snippet dummySnippet3 = new Snippet();
    dummySnippet3.setId("id3");
    dummySnippet3.setTitle("Title 3");
    dummySnippet3.setContent("Content 3");
    dummySnippet3.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet3.setDateTimeAdded(LocalDateTime.of(2015, Month.SEPTEMBER, 11, 0, 0));
    dummySnippet3.setDeleted(true);

    Snippet dummySnippet4 = new Snippet();
    dummySnippet4.setId("id4");
    dummySnippet4.setTitle("Title 4");
    dummySnippet4.setContent("Content 4");
    dummySnippet4.setVisibility(Snippet.Visibility.UNLISTED);
    dummySnippet4.setDateTimeAdded(LocalDateTime.of(2015, Month.SEPTEMBER, 12, 0, 0));
    dummySnippet4.setDeleted(false);

    dummySnippets.add(dummySnippet1);
    dummySnippets.add(dummySnippet2);
    dummySnippets.add(dummySnippet3);
    dummySnippets.add(dummySnippet4);
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

    Snippet snippet = snippetRepository.findAll().collect(toList()).get(0);

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
  @SuppressWarnings("unchecked")
  public void browse_ShouldHaveNotDeletedPublicSnippetList() throws Exception {
    dummySnippets.forEach(snippetRepository::save);

    mockMvc.perform(get("/browse"))
           .andExpect(model().attribute("snippets", hasSize(2)))
           .andExpect(model().attribute("snippets", hasItems(
                 hasProperty("title", is("Title 1")),
                 hasProperty("content", is("Content 2"))
               )));
  }

  @Test
  public void browse_ShouldHaveSnippetListSortedByDateTimeAddedDesc()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(get("/browse"))
           .andExpect(model().attribute("snippets",
               contains(dummySnippets.get(1), dummySnippets.get(0))));
  }


}
