package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.snippet.Snippet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class DeleteSnippetsIntegrationTests extends SnippetsIntegrationTests {

  private MockHttpServletRequestBuilder deleteSnippetRequestWrongOwnerPassword;

  private MockHttpServletRequestBuilder deleteSnippetWoOwnerPasswordSetRequest;

  private MockHttpServletRequestBuilder deleteSnippetRequestValid;

  @Override
  @Before
  public void setUp() {
    super.setUp();

    deleteSnippetRequestWrongOwnerPassword =
        MockMvcRequestBuilders.post("/" + dummySnippets.get(0).getId())
            .param("delete", "")
            .param("ownerPassword", "wrongpass");

    deleteSnippetWoOwnerPasswordSetRequest =
        MockMvcRequestBuilders.post("/" + dummySnippets.get(1).getId())
            .param("delete", "")
            .param("ownerPassword", "somepass");

    deleteSnippetRequestValid =
        MockMvcRequestBuilders.post("/" + dummySnippets.get(0).getId())
            .param("delete", "")
            .param("ownerPassword", "pass");
  }

  @Test
  public void doDelete_WrongOwnerPassword_ShouldRedirectToViewSnippetPage()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestWrongOwnerPassword)
           .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
           .andExpect(MockMvcResultMatchers.view().name("redirect:/{snippetId}"))
           .andExpect(MockMvcResultMatchers.redirectedUrlPattern("/" + dummySnippet.getId() + "*"));
  }

  @Test
  public void doDelete_WrongOwnerPassword_ShouldNotDeleteSnippet()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestWrongOwnerPassword);

    Optional<Snippet> optionalSnippet =
        snippetRepository.findById(dummySnippet.getId());

    MatcherAssert.assertThat(optionalSnippet.isPresent(), Matchers.is(true));
    MatcherAssert.assertThat(optionalSnippet.get().isDeleted(), Matchers.is(false));
  }

  @Test
  public void doDelete_SnippetWoOwnerPasswordSet_ShouldNotDeleteSnippet()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(1);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetWoOwnerPasswordSetRequest);

    Optional<Snippet> optionalSnippet =
        snippetRepository.findById(dummySnippet.getId());

    MatcherAssert.assertThat(optionalSnippet.isPresent(), Matchers.is(true));
    MatcherAssert.assertThat(optionalSnippet.get().isDeleted(), Matchers.is(false));
  }

  @Test
  public void doDelete_ShouldRedirectToBrowseSnippetsPage()
      throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestValid)
           .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
           .andExpect(MockMvcResultMatchers.view().name("redirect:/browse"))
           .andExpect(MockMvcResultMatchers.redirectedUrl("/browse"));
  }

  @Test
  public void doDelete_ShouldDeleteSnippet() throws Exception {
    Snippet dummySnippet = dummySnippets.get(0);

    snippetRepository.save(dummySnippet);

    mockMvc.perform(deleteSnippetRequestValid);

    Optional<Snippet> optionalSnippet =
        snippetRepository.findById(dummySnippet.getId());

    MatcherAssert.assertThat(optionalSnippet.isPresent(), Matchers.is(true));
    MatcherAssert.assertThat(optionalSnippet.get().isDeleted(), Matchers.is(true));
  }

}
