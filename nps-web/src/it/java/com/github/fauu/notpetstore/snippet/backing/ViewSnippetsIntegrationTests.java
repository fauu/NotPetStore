package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.feedback.ExceptionFeedback;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetVisit;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.UUID;

public class ViewSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Test
  public void view_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/id123456789"))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void view_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId()))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void view_ShouldTryToRenderViewSnippetPage() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId()))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.view().name("view"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/view.*"));
  }

  @Test
  public void view_ShouldHaveRequestedSnippet() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId()))
           .andExpect(MockMvcResultMatchers.model().attribute("snippet", Matchers.is(dummySnippet)));
  }

  @Test
  public void view_NewVisitor_ShouldHaveVisitorIdCookie() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId()))
           .andExpect(MockMvcResultMatchers.cookie().exists("npsVisitorId"));
  }

  @Test
  public void view_ReturningVisitor_ShouldHaveSnippetWithNotIncrementedNumViews()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    SnippetVisit previousVisit = new SnippetVisit();
    previousVisit.setSnippetId(dummySnippet.getId());
    previousVisit.setVisitorId(UUID.randomUUID());
    previousVisit.setDateTime(LocalDateTime.now().minusDays(1));

    snippetVisitRepository.save(previousVisit);

    Cookie visitorIdCookie =
        new Cookie("npsVisitorId", previousVisit.getVisitorId().toString());

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId()).cookie(visitorIdCookie))
           .andExpect(MockMvcResultMatchers.model().attribute("snippet",
               Matchers.hasProperty("numViews", Matchers.is(dummySnippet.getNumViews()))));
  }

  @Test
  public void viewRaw_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/id123456789/raw"))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void viewRaw_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId() + "/raw"))
           .andExpect(MockMvcResultMatchers.status().isNotFound())
           .andExpect(MockMvcResultMatchers.view().name("exception"))
           .andExpect(MockMvcResultMatchers.forwardedUrlPattern("/**/exception.*"))
           .andExpect(MockMvcResultMatchers.model().attribute("exceptionFeedback",
               Matchers.is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void viewRaw_ShouldHaveRawRequestedSnippet() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(MockMvcRequestBuilders.get("/" + dummySnippet.getId() + "/raw"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("text/plain"))
           .andExpect(MockMvcResultMatchers.content().string(dummySnippet.getContent()));
  }

}
