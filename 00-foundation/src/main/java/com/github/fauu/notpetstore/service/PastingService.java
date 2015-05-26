package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.util.IdGenerator;
import com.github.fauu.notpetstore.web.exception.RequestedSnippetDeletedException;
import com.github.fauu.notpetstore.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PastingService {

  // TODO: Externalize this?
  private static final int NEW_SNIPPET_ID_LENGTH = 8;

  @Autowired
  private SnippetRepository snippetRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostConstruct
  public void init() {
    addDummySnippets();
  }

  public Snippet getNonDeletedSnippetById(String id) {
    Snippet snippet =
        snippetRepository.findById(id)
                         .orElseThrow(ResourceNotFoundException::new);

    if (snippet.isDeleted()) {
      throw new RequestedSnippetDeletedException();
    }

    return snippet;
  }

  public List<Snippet> getNonDeletedPublicSnippetsSortedByDateTimeAddedDesc() {
    return snippetRepository.findByDeletedFalseAndVisibilityPublic()
        .sorted(Comparator.comparing(Snippet::getDateTimeAdded).reversed())
        .collect(toList());
  }

  public Snippet addSnippet(SnippetForm snippetForm) {
    Snippet snippet = new Snippet();

    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());
    snippet.setSyntaxHighlighting(snippetForm.getSyntaxHighlighting());
    snippet.setVisibility(snippetForm.getVisibility());

    String ownerPassowrd = snippetForm.getOwnerPassword();
    if (ownerPassowrd != null) {
      String encodedOwnerPassword = bCryptPasswordEncoder.encode(ownerPassowrd);
      snippet.setOwnerPassword(encodedOwnerPassword);
    }

    snippet.setDateTimeAdded(LocalDateTime.now());
    snippet.setNumViews(0);
    snippet.setDeleted(false);

    return snippetRepository.save(snippet);
  }

  public Snippet deleteSnippet(Snippet snippet) {
    snippet.setDeleted(true);

    return snippetRepository.save(snippet);
  }

  public boolean verifySnippetOwnerPassword(Snippet snippet,
                                            String ownerPassword) {
    return bCryptPasswordEncoder.matches(ownerPassword,
                                         snippet.getOwnerPassword());
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
    snippet.setTitle("TransientSnippetRepository.java");
    snippet.setContent("package com.github.fauu.notpetstore.repository;\n\nimport com.github.fauu.notpetstore.model.entity.Snippet;\nimport org.springframework.stereotype.Repository;\n\nimport java.util.LinkedList;\nimport java.util.List;\nimport java.util.Optional;\nimport java.util.stream.Stream;\n\n@Repository\npublic class TransientSnippetRepository implements SnippetRepository {\n\n  private List<Snippet> snippetStore;\n\n  public TransientSnippetRepository() {\n    snippetStore = new LinkedList<>();\n  }\n\n  @Override\n  public boolean exists(String id) {\n    return snippetStore.stream()\n                       .anyMatch(s -> s.getId().equals(id));\n  }\n\n  @Override\n  public Optional<Snippet> findById(String id) {\n    return snippetStore.stream()\n                       .filter(s -> s.getId().equals(id))\n                       .findFirst();\n  }\n\n  @Override\n  public Stream<Snippet> findAll() {\n    return snippetStore.stream();\n  }\n\n  @Override\n  public Stream<Snippet> findByDeletedFalseAndVisibilityPublic() {\n    return snippetStore.stream()\n        .filter(s -> !s.isDeleted())\n        .filter(s -> s.getVisibility().equals(Snippet.Visibility.PUBLIC));\n  }\n\n  @Override\n  public Snippet save(Snippet snippet) {\n    snippetStore.removeIf(s -> s.equals(snippet));\n    snippetStore.add(snippet);\n\n    return snippet;\n  }\n\n  @Override\n  public void deleteAll() {\n    snippetStore.clear();\n  }\n\n}");
    snippet.setSyntaxHighlighting(Snippet.SyntaxHighlighting.JAVA);
    snippet.setOwnerPassword(bCryptPasswordEncoder.encode("password"));
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippet.setDateTimeAdded(LocalDateTime.now().minusDays(15).minusHours(3));
    snippet.setNumViews(0);
    snippet.setDeleted(false);
    snippetRepository.save(snippet);

    snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle(null);
    snippet.setContent("Praesent hendrerit risus at dui congue, id dictum nibh semper. Praesent ornare neque vel ex placerat, sit amet pulvinar massa porttitor. Donec scelerisque vestibulum dolor tempor molestie. Duis condimentum elementum efficitur. Cras sollicitudin condimentum rutrum. Suspendisse sapien metus, porta quis tortor ut, interdum sodales tellus. Nunc sit amet diam tempor, molestie sapien sed, malesuada neque. Nulla facilisi. Pellentesque non dictum felis. Nulla luctus in nunc nec iaculis. Quisque tellus justo, sodales quis nulla non, faucibus ullamcorper urna. Nunc luctus, orci nec sodales finibus, justo tortor blandit quam, a porta risus leo pharetra orci. Sed a justo non eros rhoncus dapibus id efficitur enim. Donec maximus consequat metus vitae tempus. Duis viverra lorem libero, sit amet bibendum justo eleifend nec. Mauris eros lorem, congue non dui sit amet, pulvinar sodales ante.");
    snippet.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippet.setOwnerPassword(null);
    snippet.setDateTimeAdded(LocalDateTime.now().minusHours(5));
    snippet.setNumViews(0);
    snippet.setDeleted(false);
    snippetRepository.save(snippet);

    snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle("Deleted snippet");
    snippet.setContent("Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.");
    snippet.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippet.setOwnerPassword(bCryptPasswordEncoder.encode("password"));
    snippet.setDateTimeAdded(LocalDateTime.now().minusMinutes(3));
    snippet.setNumViews(0);
    snippet.setDeleted(true);
    snippetRepository.save(snippet);

    snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle("Vivamus in massa a dui sodales finibus");
    snippet.setContent("Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.");
    snippet.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    snippet.setVisibility(Snippet.Visibility.UNLISTED);
    snippet.setOwnerPassword(bCryptPasswordEncoder.encode("password"));
    snippet.setDateTimeAdded(LocalDateTime.now().minusMinutes(1));
    snippet.setNumViews(0);
    snippet.setDeleted(false);
    snippetRepository.save(snippet);
  }

}
