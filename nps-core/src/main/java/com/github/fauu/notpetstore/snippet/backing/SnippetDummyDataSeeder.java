package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.snippet.Snippet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
class SnippetDummyDataSeeder {

  private @Autowired SnippetRepository snippetRepository;

  private @Autowired SnippetUniqueIdGenerator snippetUniqueIdGenerator;

  private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

  private @Autowired BeanFactory beanFactory;

  @PostConstruct
  private void seedDummySnippetDataAndDestroy() {
    int numDummyRecords = 0;

    Snippet snippet;
    String id;
    String content;
    LocalDateTime dateTimeAdded;

    id = snippetUniqueIdGenerator.generateUniqueId();
    content = "package com.github.fauu.notpetstore.repository;\n\nimport com.github.fauu.notpetstore.snippet.Snippet;\nimport org.springframework.stereotype.Repository;\n\nimport java.util.LinkedList;\nimport java.util.List;\nimport java.util.Optional;\nimport java.util.stream.Stream;\n\n@Repository\npublic class TransientSnippetRepository implements SnippetRepository {\n\n  private List<Snippet> snippetStore;\n\n  public TransientSnippetRepository() {\n    snippetStore = new LinkedList<>();\n  }\n\n  @Override\n  public boolean exists(String id) {\n    return snippetStore.stream()\n                       .anyMatch(s -> s.getId().equals(id));\n  }\n\n  @Override\n  public Optional<Snippet> findById(String id) {\n    return snippetStore.stream()\n                       .filter(s -> s.getId().equals(id))\n                       .findFirst();\n  }\n\n  @Override\n  public Stream<Snippet> findAll() {\n    return snippetStore.stream();\n  }\n\n  @Override\n  public Stream<Snippet> findByDeletedFalseAndVisibilityPublic() {\n    return snippetStore.stream()\n        .filter(s -> !s.isDeleted())\n        .filter(s -> s.getVisibility().equals(Snippet.Visibility.PUBLIC));\n  }\n\n  @Override\n  public Snippet save(Snippet snippet) {\n    snippetStore.removeIf(s -> s.equals(snippet));\n    snippetStore.add(snippet);\n\n    return snippet;\n  }\n\n  @Override\n  public void deleteAll() {\n    snippetStore.clear();\n  }\n\n}";
    dateTimeAdded = LocalDateTime.now().minusDays(15).minusHours(3);
    snippet = new Snippet(id, content, dateTimeAdded);
    snippet.setTitle("TransientSnippetRepository.java");
    snippet.setSyntaxHighlighting(Snippet.SyntaxHighlighting.JAVA);
    snippet.setOwnerPassword(bCryptPasswordEncoder.encode("password"));
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippetRepository.save(snippet);
    numDummyRecords++;

    id = snippetUniqueIdGenerator.generateUniqueId();
    content = "Praesent hendrerit risus at dui congue, id dictum nibh semper. Praesent ornare neque vel ex placerat, sit amet pulvinar massa porttitor. Donec scelerisque vestibulum dolor tempor molestie. Duis condimentum elementum efficitur. Cras sollicitudin condimentum rutrum. Suspendisse sapien metus, porta quis tortor ut, interdum sodales tellus. Nunc sit amet diam tempor, molestie sapien sed, malesuada neque. Nulla facilisi. Pellentesque non dictum felis. Nulla luctus in nunc nec iaculis. Quisque tellus justo, sodales quis nulla non, faucibus ullamcorper urna. Nunc luctus, orci nec sodales finibus, justo tortor blandit quam, a porta risus leo pharetra orci. Sed a justo non eros rhoncus dapibus id efficitur enim. Donec maximus consequat metus vitae tempus. Duis viverra lorem libero, sit amet bibendum justo eleifend nec. Mauris eros lorem, congue non dui sit amet, pulvinar sodales ante.";
    dateTimeAdded = LocalDateTime.now().minusHours(5);
    snippet = new Snippet(id, content, dateTimeAdded);
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippetRepository.save(snippet);
    numDummyRecords++;

    id = snippetUniqueIdGenerator.generateUniqueId();
    content = "Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.";
    dateTimeAdded = LocalDateTime.now().minusMinutes(3);
    snippet = new Snippet(id, content, dateTimeAdded);
    snippet.setTitle("Deleted snippet");
    snippet.setVisibility(Snippet.Visibility.PUBLIC);
    snippet.setOwnerPassword(bCryptPasswordEncoder.encode("password"));
    snippet.setDeleted(true);
    snippetRepository.save(snippet);
    numDummyRecords++;

    id = snippetUniqueIdGenerator.generateUniqueId();
    content = "Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.";
    dateTimeAdded = LocalDateTime.now().minusMinutes(1);
    snippet = new Snippet(id, content, dateTimeAdded);
    snippet.setTitle("Vivamus in massa a dui sodales finibus");
    snippet.setOwnerPassword(bCryptPasswordEncoder.encode("password"));
    snippetRepository.save(snippet);
    numDummyRecords++;

    Random random = new Random();
    for (int i = 0; i < 50; i++) {
      id = snippetUniqueIdGenerator.generateUniqueId();
      content = "Morbi pharetra, arcu sed molestie faucibus, justo eros tempus eros, accumsan laoreet risus diam eu turpis. Aenean ultrices nisi ex, et blandit nulla tincidunt id. Mauris aliquet eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus in massa a dui sodales finibus. Vivamus sollicitudin viverra nisi, in consectetur velit imperdiet ac. Fusce vehicula leo ut erat lobortis euismod.";
      dateTimeAdded = LocalDateTime.now().minusDays(i);
      snippet = new Snippet(id, content, dateTimeAdded);
      snippet.setTitle("Test Snippet " + random.nextInt(10000));
      Snippet.SyntaxHighlighting[] syntaxHighlightingValues = Snippet.SyntaxHighlighting.values();
      snippet.setSyntaxHighlighting(syntaxHighlightingValues[random.nextInt(syntaxHighlightingValues.length)]);
      snippet.setVisibility(Snippet.Visibility.PUBLIC);
      snippet.setOwnerPassword(bCryptPasswordEncoder.encode("password"));
      snippet.setNumViews(random.nextInt(200));
      snippetRepository.save(snippet);
      numDummyRecords++;
    }

    ((DefaultListableBeanFactory) beanFactory).destroySingleton(this.getClass().getSimpleName());

    log.info("Seeded dummy snippet data (" + numDummyRecords + " records) " +
             "and destroyed SnippetDummyDataSeeder");
  }

}
