package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.feedback.ExceptionFeedback;
import com.github.fauu.notpetstore.snippet.Snippet;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DownloadSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Test
  public void download_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(get("/id123456789/download"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void download_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId() + "/download"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void download_ShouldHaveRequestedSnippetAsFileAttachment()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId() + "/download"))
           .andExpect(status().isOk())
           .andExpect(header().string("Content-Disposition",
               "attachment;filename=" + dummySnippet.getFilename() + ".txt"))
           .andExpect(content().contentTypeCompatibleWith("text/plain"))
           .andExpect(content().string(dummySnippet.getContent()));
  }

}
