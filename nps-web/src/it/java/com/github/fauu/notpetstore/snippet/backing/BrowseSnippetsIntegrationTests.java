package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.snippet.Snippet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BrowseSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Value("${web.browseSnippets.pageSize}")
  private int maxPageSize;

  @Test
  public void browse_ShouldRedirectToBrowseSnippetsPage1Page()
      throws Exception {
    mockMvc.perform(get("/browse"))
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/browse/page/1"))
           .andExpect(redirectedUrl("/browse/page/1"));
  }

  @Test
  public void browse_InvalidPageNo_ShouldReturn400() throws Exception {
    mockMvc.perform(get("/browse/page/-1"))
           .andExpect(status().isBadRequest());
  }

  @Test
  public void browse_NonexistentPage_ShouldReturn404() throws Exception {
    dummySnippets.forEach(snippetRepository::save);

    mockMvc.perform(get("/browse/page/100"))
           .andExpect(status().isNotFound());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void browse_NonEmptyPage_ShouldHaveNonDeletedPublicSnippetPage()
      throws Exception {
    dummySnippets.forEach(snippetRepository::save);

    mockMvc.perform(get("/browse/page/1"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items", hasSize(2))))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items", hasItems(
                   hasProperty("title",
                       is(dummySnippets.get(0).getTitle())),
                   hasProperty("content",
                       is(dummySnippets.get(1).getContent()))))));
  }

  @Test
  public void browse_NonEmptyPage_ShouldHaveSnippetPageSortedByCreatedAtDesc()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(get("/browse/page/1"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items",
                   contains(dummySnippets.get(1), dummySnippets.get(0)))));
  }

  @Test
  public void browse_SortTypePopular_ShouldHaveSnippetPageSortedByViewCountDesc()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(get("/browse/page/1?sort=popular"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items",
                   contains(dummySnippets.get(0), dummySnippets.get(1)))));
  }

  @Test
  public void browse_SyntaxHighlightingFilterJava_ShouldHaveJavaFilteredSyntaxName()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    String syntaxCode = "java";

    mockMvc.perform(get("/browse/page/1?syntax=" + syntaxCode))
           .andExpect(model().attribute("filteredSyntaxName",
               is(Snippet.SyntaxHighlighting.ofCode(syntaxCode)
                                            .get()
                                            .toString())));
  }

  @Test
  public void browse_SyntaxHighlightingFilterJava_ShouldHaveSnippetPageWithOnlyJavaSnippets()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(get("/browse/page/1?syntax=java"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items",
                   contains(dummySnippets.get(1)))));
  }

  @Test
  public void browse_FullPage_ShouldHaveSnippetPageOfMaxSize()
      throws Exception {
    Snippet dummySnippet;
    Random random = new Random();
    LocalDateTime now = LocalDateTime.now();
    for (int i = 0; i < 15; i++) {
      dummySnippet =
          new Snippet("id" + i, "content", now.minusDays(random.nextInt(100)));

      dummySnippet.setViewCount(random.nextInt(100));
      dummySnippet.setVisibility(Snippet.Visibility.PUBLIC);

      snippetRepository.save(dummySnippet);
    }

    mockMvc.perform(get("/browse/page/1"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items", hasSize(maxPageSize))));
  }

}
