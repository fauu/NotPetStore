package com.github.fauu.notpetstore.test;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.service.SnippetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class SnippetControllerTests extends AbstractContextControllerTests {

  private MockMvc mockMvc;

  @Autowired
  private SnippetService snippetServiceMock;

  @Before
  public void setup() {
    Mockito.reset(snippetServiceMock);

    mockMvc = webAppContextSetup(context).build();
  }

  @Test
  public void add_ShouldRenderAddFormWithEmptyFormObject() throws Exception {
    mockMvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(model().attribute("snippetForm",
               allOf(
                   hasProperty("title", is(nullValue())),
                   hasProperty("content", is(nullValue()))
               )));

    verifyZeroInteractions(snippetServiceMock);
  }

  @Test
  public void doAdd_EmptyContent_ShouldRenderAddFormWithValidationErrorForContent()
      throws Exception {
    SnippetForm snippetForm = new SnippetForm();
    snippetForm.setTitle("title");
    snippetForm.setContent("");

    mockMvc.perform(post("/").param("title", snippetForm.getTitle())
                             .param("content", snippetForm.getContent()))
           .andExpect(status().isOk())
           .andExpect(view().name("add"))
           .andExpect(model().attribute("snippetForm",
               allOf(
                   hasProperty("title", is("title")),
                   hasProperty("content", isEmptyString())
               )))
           .andExpect(model().attributeHasFieldErrors("snippetForm", "content"));

    verifyZeroInteractions(snippetServiceMock);
  }

  @Test
  public void doAdd_ValidForm_ShouldStoreSnippetAndRedirectToRoot()
      throws Exception {
    SnippetForm snippetForm = new SnippetForm();
    snippetForm.setTitle("title");
    snippetForm.setContent("content");

    Snippet snippet = new Snippet();
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());

    when(snippetServiceMock.add(snippetForm)).thenReturn(snippet);

    mockMvc.perform(post("/").param("title", snippetForm.getTitle())
                             .param("content", snippetForm.getContent()))
           .andExpect(status().is3xxRedirection())
           .andExpect(view().name("redirect:/"));

    verify(snippetServiceMock, times(1)).add(snippetForm);
    verifyNoMoreInteractions(snippetServiceMock);
  }

  @Test
  public void browse_ShouldRenderBrowseSnippetsPageWithSnippetList()
      throws Exception {
    Snippet firstSnippet = new Snippet();
    firstSnippet.setTitle("First snippet title");
    firstSnippet.setContent("First snippet content");

    Snippet secondSnippet = new Snippet();
    secondSnippet.setTitle("Second snippet title");
    secondSnippet.setContent("Second snippet content");

    when(snippetServiceMock.findAll())
        .thenReturn(Arrays.asList(firstSnippet, secondSnippet));

    mockMvc.perform(get("/browse"))
           .andExpect(status().isOk())
           .andExpect(view().name("browse"))
           .andExpect(model().attribute("snippets", hasSize(2)))
           .andExpect(model().attribute("snippets", hasItem(
               allOf(
                   instanceOf(Snippet.class),
                   hasProperty("title", is("Second snippet title")),
                   hasProperty("content", is("Second snippet content"))
               ))));

    verify(snippetServiceMock, times(1)).findAll();
    verifyNoMoreInteractions(snippetServiceMock);
  }

}
