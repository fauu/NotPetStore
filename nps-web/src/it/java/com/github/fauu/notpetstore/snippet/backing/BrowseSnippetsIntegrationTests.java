package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.backing.IdGenerator;
import com.github.fauu.notpetstore.snippet.Snippet;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Random;

public class BrowseSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Test
  public void browse_ShouldRedirectToBrowseSnippetsPage1Page() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/browse"))
           .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
           .andExpect(MockMvcResultMatchers.view().name("redirect:/browse/page/1"))
           .andExpect(MockMvcResultMatchers.redirectedUrl("/browse/page/1"));
  }

  @Test
  public void browse_InvalidPageNo_ShouldReturn400() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/-1"))
           .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void browse_NonexistentPage_ShouldReturn404() throws Exception {
    dummySnippets.forEach(snippetRepository::save);

    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/100"))
           .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void browse_NonEmptyPage_ShouldHaveNonDeletedPublicSnippetPage() throws Exception {
    dummySnippets.forEach(snippetRepository::save);

    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/1"))
           .andExpect(MockMvcResultMatchers.model().attribute("snippetPage",
               Matchers.hasProperty("items", Matchers.hasSize(2))))
           .andExpect(MockMvcResultMatchers.model().attribute("snippetPage",
               Matchers.hasProperty("items", Matchers.hasItems(
                   Matchers.hasProperty("title", Matchers.is("Title 1")),
                   Matchers.hasProperty("content", Matchers.is("Content 2"))))));
  }

  @Test
  public void browse_NonEmptyPage_ShouldHaveSnippetPageSortedByDateTimeAddedDesc()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/1"))
           .andExpect(MockMvcResultMatchers.model().attribute("snippetPage",
               Matchers.hasProperty("items",
                   Matchers.contains(dummySnippets.get(1), dummySnippets.get(0)))));
  }

  @Test
  public void browse_SortCodePopular_ShouldHaveSnippetPageSortedByNumViewsDesc()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/1?sort=popular"))
           .andExpect(MockMvcResultMatchers.model().attribute("snippetPage",
               Matchers.hasProperty("items",
                   Matchers.contains(dummySnippets.get(0), dummySnippets.get(1)))));
  }

  @Test
  public void browse_SyntaxHighlightingFilterJava_ShouldHaveJavaFilteredSyntaxName()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/1?syntax=java"))
           .andExpect(MockMvcResultMatchers.model().attribute("filteredSyntaxName",
               Matchers.is(Snippet.SyntaxHighlighting.fromCode("java").get())));
  }

  @Test
  public void browse_SyntaxHighlightingFilterJava_ShouldHaveSnippetPageWithOnlyJavaSnippets()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/1?syntax=java"))
           .andExpect(MockMvcResultMatchers.model().attribute("snippetPage",
               Matchers.hasProperty("items", Matchers.contains(dummySnippets.get(1)))));
  }

  @Test
  public void browse_FullPage_ShouldHaveSnippetPageOfSize10() throws Exception {
    Snippet dummySnippet;
    Random random = new Random();
    for (int i = 0; i < 15; i++) {
      dummySnippet = new Snippet();

      dummySnippet.setId(IdGenerator.generate(8));
      dummySnippet.setDateTimeAdded(
          LocalDateTime.now().minusDays(random.nextInt(100)));
      dummySnippet.setNumViews(random.nextInt(100));
      dummySnippet.setVisibility(Snippet.Visibility.PUBLIC);
      dummySnippet.setDeleted(false);

      snippetRepository.save(dummySnippet);
    }

    mockMvc.perform(MockMvcRequestBuilders.get("/browse/page/1"))
           .andExpect(MockMvcResultMatchers.model().attribute("snippetPage",
               Matchers.hasProperty("items", Matchers.hasSize(10))));
  }

}
