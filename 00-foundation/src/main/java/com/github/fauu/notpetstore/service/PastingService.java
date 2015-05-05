package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.service.exception.ServiceException;
import com.github.fauu.notpetstore.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PastingService {

  // TODO: Externalize this?
  private static final int NEW_SNIPPET_ID_LENGTH = 8;

  @Autowired
  private SnippetRepository snippetRepository;

  @PostConstruct
  public void init() {
    addDummySnippets();
  }

  public List<Snippet> getAllSnippets() throws ServiceException {
    return snippetRepository.findAll();
  }

  public Snippet addSnippet(SnippetForm snippetForm) {
    Snippet snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());
    snippet.setVisibility(snippetForm.getVisibility());
    snippet.setDateTimeAdded(LocalDateTime.now());
    snippet.setNumViews(0);

    return snippetRepository.save(snippet);
  }

  private String generateUniqueId(int length) {
    String id;
    do {
      id = IdGenerator.generate(length);
    } while (snippetRepository.exists(id));

    return id;
  }

  private void addDummySnippets() {
    SnippetForm snippetForm = new SnippetForm();

    snippetForm.setTitle("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
    snippetForm.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla euismod nulla nec ex cursus, eu vestibulum eros iaculis. Integer ullamcorper orci et elit eleifend posuere. Vestibulum mattis eros eget libero facilisis, vitae pretium velit tincidunt. Pellentesque faucibus risus quis mauris consectetur, nec interdum arcu sagittis. Nullam ullamcorper sem dui, in aliquet urna luctus sed. Etiam finibus, nibh ut aliquet pharetra, leo enim blandit elit, vel tempus sem urna nec risus. Nullam mattis libero enim, id dignissim nulla ultricies eget. Nullam vitae scelerisque magna. Integer vitae blandit lorem, vel vehicula tortor. Vestibulum at efficitur sem, at consectetur quam. Aenean efficitur vitae dui dapibus pretium.");
    snippetForm.setVisibility(Snippet.Visibility.PUBLIC);
    addSnippet(snippetForm);

    snippetForm.setTitle("Praesent hendrerit risus at dui congue, id dictum nibh semper");
    snippetForm.setContent("Praesent hendrerit risus at dui congue, id dictum nibh semper. Praesent ornare neque vel ex placerat, sit amet pulvinar massa porttitor. Donec scelerisque vestibulum dolor tempor molestie. Duis condimentum elementum efficitur. Cras sollicitudin condimentum rutrum. Suspendisse sapien metus, porta quis tortor ut, interdum sodales tellus. Nunc sit amet diam tempor, molestie sapien sed, malesuada neque. Nulla facilisi. Pellentesque non dictum felis. Nulla luctus in nunc nec iaculis. Quisque tellus justo, sodales quis nulla non, faucibus ullamcorper urna. Nunc luctus, orci nec sodales finibus, justo tortor blandit quam, a porta risus leo pharetra orci. Sed a justo non eros rhoncus dapibus id efficitur enim. Donec maximus consequat metus vitae tempus. Duis viverra lorem libero, sit amet bibendum justo eleifend nec. Mauris eros lorem, congue non dui sit amet, pulvinar sodales ante.");
    snippetForm.setVisibility(Snippet.Visibility.PUBLIC);
    addSnippet(snippetForm);

    snippetForm.setTitle("Vivamus in massa a dui sodales finibus");
    snippetForm.setContent("Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.");
    snippetForm.setVisibility(Snippet.Visibility.PUBLIC);
    addSnippet(snippetForm);
  }

}
