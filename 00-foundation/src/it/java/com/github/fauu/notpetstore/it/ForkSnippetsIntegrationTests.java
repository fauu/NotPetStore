package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.web.feedback.ExceptionFeedback;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ForkSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Test
  public void fork_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(get("/fork/id123456789"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void fork_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/fork/" + dummySnippet.getId()))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void fork_ShouldTryToRenderAddSnippetPage() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/fork/" + dummySnippet.getId()))
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void fork_ShouldHavePartiallyFilledOutSnippetForm() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/fork/" + dummySnippet.getId()))
           .andExpect(model().attribute("snippetForm", allOf(
               hasProperty("title", is(dummySnippet.getTitle())),
               hasProperty("content", is(dummySnippet.getContent())),
               hasProperty("syntaxHighlighting",
                   is(dummySnippet.getSyntaxHighlighting())),
               hasProperty("expirationMoment", isEmptyOrNullString()),
               hasProperty("visibility", is(dummySnippet.getVisibility())),
               hasProperty("ownerPassword", isEmptyOrNullString())
           )));
  }

}
