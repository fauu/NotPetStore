package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetVisit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.fauu.notpetstore.common.feedback.ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT;
import static com.github.fauu.notpetstore.common.feedback.ExceptionFeedback.REQUESTED_SNIPPET_DELETED;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ViewSnippetsIntegrationTests extends SnippetsIntegrationTests {

  @Value("${web.visitorIdCookieName}")
  private String visitorIdCookieName;

  @Test
  public void view_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(get("/id123456789"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(PAGE_NOT_FOUND_DEFAULT)));
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
               is(REQUESTED_SNIPPET_DELETED)));
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
           .andExpect(cookie().exists(visitorIdCookieName));
  }

  @Test
  public void view_ReturningVisitor_ShouldHaveSnippetWithNotIncrementedNumViews()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    SnippetVisit previousVisit =
        new SnippetVisit(dummySnippet.getId(),
                         UUID.randomUUID(),
                         LocalDateTime.now().minusDays(1));

    snippetVisitRepository.save(previousVisit);

    Cookie visitorIdCookie =
        new Cookie(visitorIdCookieName,
                   previousVisit.getVisitorId().toString());

    mockMvc.perform(get("/" + dummySnippet.getId()).cookie(visitorIdCookie))
           .andExpect(model().attribute("snippet",
               hasProperty("numViews", is(dummySnippet.getNumViews()))));
  }

  @Test
  public void viewRaw_NonexistentSnippet_ShouldReturn404WithPageNotFoundDefaultExceptionFeedback()
      throws Exception {
    mockMvc.perform(get("/id123456789/raw"))
           .andExpect(status().isNotFound())
           .andExpect(view().name("exception"))
           .andExpect(forwardedUrlPattern("/**/exception.*"))
           .andExpect(model().attribute("exceptionFeedback",
               is(PAGE_NOT_FOUND_DEFAULT)));
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
               is(REQUESTED_SNIPPET_DELETED)));
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
