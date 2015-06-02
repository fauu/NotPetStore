package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.feedback.ExceptionFeedback;
import com.github.fauu.notpetstore.snippet.Snippet;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ForkSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Test
  public void fork_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/fork/id123456789"))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void fork_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/fork/" + dummySnippet.getId()))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void fork_ShouldTryToRenderAddSnippetPage() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/fork/" + dummySnippet.getId()))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.view().name("add"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void fork_ShouldHavePartiallyFilledOutSnippetForm() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/fork/" + dummySnippet.getId()))
           .andExpect(MockMvcResultMatchers.model().attribute("snippetForm", Matchers.allOf(
               Matchers.hasProperty("title", Matchers.is(dummySnippet.getTitle())),
               Matchers.hasProperty("content", Matchers.is(dummySnippet.getContent())),
               Matchers.hasProperty("syntaxHighlighting",
                   Matchers.is(dummySnippet.getSyntaxHighlighting())),
               Matchers.hasProperty("expirationMoment", Matchers.isEmptyOrNullString()),
               Matchers.hasProperty("visibility", Matchers.is(dummySnippet.getVisibility())),
               Matchers.hasProperty("ownerPassword", Matchers.isEmptyOrNullString())
           )));
  }

}
