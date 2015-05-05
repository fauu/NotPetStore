package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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

  public Optional<Snippet> getById(String id) {
    return snippetRepository.findById(id);
  }

  public List<Snippet> getNotDeletedPublicSnippetsSortedByDateTimeAddedDesc() {
    return snippetRepository.findByDeletedFalseAndVisibilityPublic()
        .sorted(Comparator.comparing(Snippet::getDateTimeAdded).reversed())
        .collect(toList());
  }

  public Snippet addSnippet(SnippetForm snippetForm) {
    Snippet snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());
    snippet.setVisibility(snippetForm.getVisibility());
    snippet.setDateTimeAdded(LocalDateTime.now());
    snippet.setNumViews(0);
    snippet.setDeleted(false);

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
    Snippet snippet;

    snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, sit");
    snippet.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla euismod nulla nec ex cursus, eu vestibulum eros iaculis. Integer ullamcorper orci et elit eleifend posuere. Vestibulum mattis eros eget libero facilisis, vitae pretium velit tincidunt. Pellentesque faucibus risus quis mauris consectetur, nec interdum arcu sagittis. Nullam ullamcorper sem dui, in aliquet urna luctus sed. Etiam finibus, nibh ut aliquet pharetra, leo enim blandit elit, vel tempus sem urna nec risus. Nullam mattis libero enim, id dignissim nulla ultricies eget. Nullam vitae scelerisque magna. Integer vitae blandit lorem, vel vehicula tortor. Vestibulum at efficitur sem, at consectetur quam. Aenean efficitur vitae dui dapibus pretium.");
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippet.setDateTimeAdded(LocalDateTime.now().minusDays(15).minusHours(3));
    snippet.setNumViews(0);
    snippet.setDeleted(false);
    snippetRepository.save(snippet);

    snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle("");
    snippet.setContent("Praesent hendrerit risus at dui congue, id dictum nibh semper. Praesent ornare neque vel ex placerat, sit amet pulvinar massa porttitor. Donec scelerisque vestibulum dolor tempor molestie. Duis condimentum elementum efficitur. Cras sollicitudin condimentum rutrum. Suspendisse sapien metus, porta quis tortor ut, interdum sodales tellus. Nunc sit amet diam tempor, molestie sapien sed, malesuada neque. Nulla facilisi. Pellentesque non dictum felis. Nulla luctus in nunc nec iaculis. Quisque tellus justo, sodales quis nulla non, faucibus ullamcorper urna. Nunc luctus, orci nec sodales finibus, justo tortor blandit quam, a porta risus leo pharetra orci. Sed a justo non eros rhoncus dapibus id efficitur enim. Donec maximus consequat metus vitae tempus. Duis viverra lorem libero, sit amet bibendum justo eleifend nec. Mauris eros lorem, congue non dui sit amet, pulvinar sodales ante.");
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippet.setDateTimeAdded(LocalDateTime.now().minusHours(5));
    snippet.setNumViews(0);
    snippet.setDeleted(false);
    snippetRepository.save(snippet);

    snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle("Deleted snippet");
    snippet.setContent("Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.");
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippet.setDateTimeAdded(LocalDateTime.now().minusMinutes(3));
    snippet.setNumViews(0);
    snippet.setDeleted(true);
    snippetRepository.save(snippet);

    snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle("Vivamus in massa a dui sodales finibus");
    snippet.setContent("Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.");
    snippet.setVisibility(Snippet.Visibility.UNLISTED);
    snippet.setDateTimeAdded(LocalDateTime.now().minusMinutes(1));
    snippet.setNumViews(0);
    snippet.setDeleted(false);
    snippetRepository.save(snippet);
  }

}
