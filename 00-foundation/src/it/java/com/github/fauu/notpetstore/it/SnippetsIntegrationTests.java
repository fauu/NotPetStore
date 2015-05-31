package com.github.fauu.notpetstore.it;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.repository.SnippetVisitRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public abstract class SnippetsIntegrationTests extends IntegrationTests {

  @Autowired
  protected SnippetRepository snippetRepository;

  @Autowired
  protected SnippetVisitRepository snippetVisitRepository;

  @Autowired
  protected BCryptPasswordEncoder bCryptPasswordEncoder;

  protected List<Snippet> dummySnippets;

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
    dummySnippet2.setSyntaxHighlighting(Snippet.SyntaxHighlighting.JAVA);
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
  }

}
