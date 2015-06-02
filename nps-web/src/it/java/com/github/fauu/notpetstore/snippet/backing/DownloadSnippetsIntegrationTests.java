package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.feedback.ExceptionFeedback;
import com.github.fauu.notpetstore.snippet.Snippet;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DownloadSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Test
  public void download_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/id123456789/download"))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void download_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId() + "/download"))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void download_ShouldHaveRequestedSnippetAsFileAttachment()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId() + "/download"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.header().string("Content-Disposition",
               "attachment;filename=" + dummySnippet.getFilename()))
           .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("text/plain"))
           .andExpect(MockMvcResultMatchers.content().string(dummySnippet.getContent()));
  }

}
