package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.IntegrationTests;
import com.github.fauu.notpetstore.snippet.Snippet;
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
    LocalDateTime dummyDate = LocalDateTime.of(2015, Month.SEPTEMBER, 9, 0, 0);

    Snippet dummySnippet1 =
        new Snippet("id1", "Content 1", dummyDate);
    dummySnippet1.setTitle("Title 1");
    dummySnippet1.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    dummySnippet1.setOwnerPassword(bCryptPasswordEncoder.encode("pass"));
    dummySnippet1.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet1.setViewCount(10);

    Snippet dummySnippet2 =
        new Snippet("id2", "Content 2", dummyDate.plusDays(1));
    dummySnippet2.setTitle("Title 2");
    dummySnippet2.setSyntaxHighlighting(Snippet.SyntaxHighlighting.JAVA);
    dummySnippet2.setVisibility(Snippet.Visibility.PUBLIC);

    Snippet dummySnippet3 =
        new Snippet("id3", "Content 3", dummyDate.plusDays(2));
    dummySnippet3.setTitle("Title 3");
    dummySnippet3.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    dummySnippet3.setVisibility(Snippet.Visibility.PUBLIC);
    dummySnippet3.setDeleted(true);

    Snippet dummySnippet4 =
        new Snippet("id4", "Content 4", dummyDate.plusDays(3));
    dummySnippet4.setTitle("Title 4");
    dummySnippet4.setSyntaxHighlighting(Snippet.SyntaxHighlighting.NONE);
    dummySnippet4.setVisibility(Snippet.Visibility.UNLISTED);

    dummySnippets.add(dummySnippet1);
    dummySnippets.add(dummySnippet2);
    dummySnippets.add(dummySnippet3);
    dummySnippets.add(dummySnippet4);
  }

}
