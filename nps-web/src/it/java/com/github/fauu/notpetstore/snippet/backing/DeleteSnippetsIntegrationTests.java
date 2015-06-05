package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.snippet.Snippet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeleteSnippetsIntegrationTests extends SnippetsIntegrationTests {

  private MockHttpServletRequestBuilder deleteSnippetRequestWrongOwnerPassword;

  private MockHttpServletRequestBuilder deleteSnippetWoOwnerPasswordSetRequest;

  private MockHttpServletRequestBuilder deleteSnippetRequestValid;

  @Override
  @Before
  public void setUp() {
    super.setUp();

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
        snippetRepository.findOne(dummySnippet.getId());

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
        snippetRepository.findOne(dummySnippet.getId());

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
        snippetRepository.findOne(dummySnippet.getId());

    assertThat(optionalSnippet.isPresent(), is(true));
    assertThat(optionalSnippet.get().isDeleted(), is(true));
  }

}
