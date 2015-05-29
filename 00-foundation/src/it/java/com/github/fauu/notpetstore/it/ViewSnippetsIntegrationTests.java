package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.entity.SnippetVisit;
import com.github.fauu.notpetstore.web.feedback.ExceptionFeedback;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ViewSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Test
  public void view_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback() throws Exception {
    mockMvc.perform(get("/id123456789"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void view_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId()))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void view_ShouldTryToRenderViewSnippetPage() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId()))
           .andExpect(status().isOk())
           .andExpect(view().name("view"))
           .andExpect(forwardedUrlPattern("/**/view.*"));
  }

  @Test
  public void view_ShouldHaveRequestedSnippet() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId()))
           .andExpect(model().attribute("snippet", is(dummySnippet)));
  }

  @Test
  public void view_NewVisitor_ShouldHaveVisitorIdCookie() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId()))
           .andExpect(cookie().exists("npsVisitorId"));
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

    mockMvc.perform(get("/" + dummySnippet.getId()).cookie(visitorIdCookie))
           .andExpect(model().attribute("snippet",
               hasProperty("numViews", is(dummySnippet.getNumViews()))));
  }

  @Test
  public void viewRaw_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback() throws Exception {
    mockMvc.perform(get("/id123456789/raw"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT)));
  }

  @Test
  public void viewRaw_DeletedSnippet_ShouldReturn404WithRequestedSnippetDeletedExceptionFeedback()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(2);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId() + "/raw"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(ExceptionFeedback.REQUESTED_SNIPPET_DELETED)));
  }

  @Test
  public void viewRaw_ShouldHaveRawRequestedSnippet() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(get("/" + dummySnippet.getId() + "/raw"))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith("text/plain"))
           .andExpect(content().string(dummySnippet.getContent()));
  }

}
