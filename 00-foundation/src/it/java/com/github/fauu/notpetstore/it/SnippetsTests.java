package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.entity.SnippetVisit;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.repository.SnippetVisitRepository;
import com.github.fauu.notpetstore.test.TestUtil;
import com.github.fauu.notpetstore.util.IdGenerator;
import com.github.fauu.notpetstore.web.feedback.ExceptionFeedback;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SnippetsTests extends AbstractIntegrationTests {

  @Autowired
  private SnippetRepository snippetRepository;

  @Autowired
  private SnippetVisitRepository snippetVisitRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private MockHttpServletRequestBuilder addSnippetRequestEmptyContent;

  private MockHttpServletRequestBuilder addSnippetRequestValid;

  private MockHttpServletRequestBuilder deleteSnippetRequestWrongOwnerPassword;

  private MockHttpServletRequestBuilder deleteSnippetWoOwnerPasswordSetRequest;

  private MockHttpServletRequestBuilder deleteSnippetRequestValid;

  private List<Snippet> dummySnippets;

  @Override
  @Before
  public void setUp() {
    super.setUp();

    snippetRepository.deleteAll();

    dummySnippets = new LinkedList<>();

    Snippet dummySnippet1 = new Snippet();
    dummySnippet1.setId("id1");
    dummySnippet1.setTitle("Title 1");
    dummySnippet1.setContent("Content 1");
    dummySnippet1.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    dummySnippet1.setOwnerPassword(bCryptPasswordEncoder.encode("pass"));
    dummySnippet1.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet1.setDateTimeAdded(
        LocalDateTime.of(2015, Month.SEPTEMBER, 9, 0, 0));
    dummySnippet1.setNumViews(10);
    dummySnippet1.setDeleted(false);

    Snippet dummySnippet2 = new Snippet();
    dummySnippet2.setId("id2");
    dummySnippet2.setTitle("Title 2");
    dummySnippet2.setContent("Content 2");
    dummySnippet2.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    dummySnippet2.setOwnerPassword(null);
    dummySnippet2.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet2.setDateTimeAdded(
        LocalDateTime.of(2015, Month.SEPTEMBER, 10, 0, 0));
    dummySnippet2.setNumViews(0);
    dummySnippet2.setDeleted(false);

    Snippet dummySnippet3 = new Snippet();
    dummySnippet3.setId("id3");
    dummySnippet3.setTitle("Title 3");
    dummySnippet3.setContent("Content 3");
    dummySnippet3.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    dummySnippet3.setOwnerPassword(null);
    dummySnippet3.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet3.setDateTimeAdded(
        LocalDateTime.of(2015, Month.SEPTEMBER, 11, 0, 0));
    dummySnippet3.setNumViews(0);
    dummySnippet3.setDeleted(true);

    Snippet dummySnippet4 = new Snippet();
    dummySnippet4.setId("id4");
    dummySnippet4.setTitle("Title 4");
    dummySnippet4.setContent("Content 4");
    dummySnippet4.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    dummySnippet4.setOwnerPassword(null);
    dummySnippet4.setVisibility(Snippet.Visibility.UNLISTED);
    dummySnippet4.setDateTimeAdded(
        LocalDateTime.of(2015, Month.SEPTEMBER, 12, 0, 0));
    dummySnippet4.setNumViews(0);
    dummySnippet4.setDeleted(false);

    dummySnippets.add(dummySnippet1);
    dummySnippets.add(dummySnippet2);
    dummySnippets.add(dummySnippet3);
    dummySnippets.add(dummySnippet4);

    addSnippetRequestEmptyContent =
        post("/").param("title", "Title")
                 .param("content", "")
                 .param("syntaxHighlighting",
                     Snippet.SyntaxHighlighting.NONE.name())
                 .param("ownerPassword", "Password")
                 .param("visibility", Snippet.Visibility.PUBLIC.name());

    addSnippetRequestValid =
        post("/").param("title", "Title")
                 .param("content", TestUtil.generateDummyString(140))
                 .param("syntaxHighlighting",
                     Snippet.SyntaxHighlighting.NONE.name())
                 .param("expirationMoment",
                     SnippetForm.ExpirationMoment.TEN_MINUTES.name())
                 .param("ownerPassword", "Password")
                 .param("visibility", Snippet.Visibility.PUBLIC.name());

    deleteSnippetRequestWrongOwnerPassword =
        post("/" + dummySnippets.get(0).getId())
            .param("delete", "")
            .param("ownerPassword", "wrongpass");

    deleteSnippetWoOwnerPasswordSetRequest =
        post("/" + dummySnippets.get(1).getId())
            .param("delete", "")
            .param("ownerPassword", "somepass");

    deleteSnippetRequestValid =
        post("/" + dummySnippets.get(0).getId())
            .param("delete", "")
            .param("ownerPassword", "pass");
  }

  @Test
  public void add_ShouldTryToRenderAddSnippetPage() throws Exception {
    mockMvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(forwardedUrlPattern("/**/add.*"));
  }

  @Test
  public void doAdd_EmptyContent_ShouldTryToRenderAddSnippetPage()
      throws Exception {
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
  public void doAdd_ShouldRedirectToBrowseSnippetsUrl() throws Exception {
    mockMvc.perform(addSnippetRequestValid)
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/browse"))
           .andExpect(redirectedUrl("/browse"));
  }

  @Test
  public void doAdd_ShouldStoreSnippet() throws Exception {
    mockMvc.perform(addSnippetRequestValid);

    Snippet snippet = snippetRepository.findAll().collect(toList()).get(0);

    assertThat(snippet.getId().length(),
        is(8));

    assertThat(snippet.getId().matches("[0-9a-zA-Z]+"),
        is(true));

    assertThat(snippet.getTitle(),
        is("Title"));

    assertThat(snippet.getContent(),
        is(TestUtil.generateDummyString(140)));

    assertThat(snippet.getSyntaxHighlighting(),
        is(Snippet.SyntaxHighlighting.NONE));

    assertThat(snippet.getDateTimeExpires()
                      .minusMinutes(10)
                      .isEqual(snippet.getDateTimeAdded()),
               is(true));

    assertThat(bCryptPasswordEncoder.matches("Password", snippet.getOwnerPassword()),
        is(true));

    assertThat(snippet.getVisibility(),
        is(Snippet.Visibility.PUBLIC));

    assertThat(snippet.getDateTimeAdded().isBefore(LocalDateTime.now()),
        is(true));

    assertThat(snippet.getNumViews(),
        is(0));
  }

  @Test
  public void browse_ShouldRedirectToBrowseSnippetsPage1Page() throws Exception {
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
  public void browse_NonEmptyPage_ShouldHaveNonDeletedPublicSnippetPage() throws Exception {
    dummySnippets.forEach(snippetRepository::save);

    mockMvc.perform(get("/browse/page/1"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items", hasSize(2))))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items", hasItems(
                   hasProperty("title", is("Title 1")),
                   hasProperty("content", is("Content 2"))))));
  }

  @Test
  public void browse_NonEmptyPage_ShouldHaveSnippetPageSortedByDateTimeAddedDesc()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(get("/browse/page/1"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items",
                   contains(dummySnippets.get(1), dummySnippets.get(0)))));
  }

  @Test
  public void browse_NonEmptyPageSortCodePopular_ShouldHaveSnippetPageSortedByNumViewsDesc()
      throws Exception {
    dummySnippets.stream().limit(2).forEachOrdered(snippetRepository::save);

    mockMvc.perform(get("/browse/page/1?sort=popular"))
        .andExpect(model().attribute("snippetPage",
            hasProperty("items",
                contains(dummySnippets.get(0), dummySnippets.get(1)))));
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

    mockMvc.perform(get("/browse/page/1"))
           .andExpect(model().attribute("snippetPage",
               hasProperty("items", hasSize(10))));
  }

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
               "attachment;filename=" + dummySnippet.getFilename()))
           .andExpect(content().contentTypeCompatibleWith("text/plain"))
           .andExpect(content().string(dummySnippet.getContent()));
  }

  @Test
  public void doDelete_WrongOwnerPassword_ShouldRedirectToViewSnippetPage()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestWrongOwnerPassword)
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/{snippetId}"))
           .andExpect(redirectedUrlPattern("/" + dummySnippet.getId() + "*"));
  }

  @Test
  public void doDelete_WrongOwnerPassword_ShouldNotDeleteSnippet()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestWrongOwnerPassword);

    Optional<Snippet> optionalSnippet =
        snippetRepository.findById(dummySnippet.getId());

    assertThat(optionalSnippet.isPresent(), is(true));
    assertThat(optionalSnippet.get().isDeleted(), is(false));
  }

  @Test
  public void doDelete_SnippetWoOwnerPasswordSet_ShouldNotDeleteSnippet()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(1);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetWoOwnerPasswordSetRequest);

    Optional<Snippet> optionalSnippet =
        snippetRepository.findById(dummySnippet.getId());

    assertThat(optionalSnippet.isPresent(), is(true));
    assertThat(optionalSnippet.get().isDeleted(), is(false));
  }

  @Test
  public void doDelete_ShouldRedirectToBrowseSnippetsPage()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestValid)
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/browse"))
           .andExpect(redirectedUrl("/browse"));
  }

  @Test
  public void doDelete_ShouldDeleteSnippet() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestValid);

    Optional<Snippet> optionalSnippet =
        snippetRepository.findById(dummySnippet.getId());

    assertThat(optionalSnippet.isPresent(), is(true));
    assertThat(optionalSnippet.get().isDeleted(), is(true));
  }

}
