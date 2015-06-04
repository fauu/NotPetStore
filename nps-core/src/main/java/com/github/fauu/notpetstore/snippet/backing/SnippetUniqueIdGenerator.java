package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.backing.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class SnippetUniqueIdGenerator {

  private @Autowired SnippetRepository snippetRepository;

  private @Autowired StringGenerator stringGenerator;

  @Value("${snippet.idLength}")
  private int idLength;

  @Value("${snippet.idSymbols}")
  private char[] idSymbols;

  public String generateUniqueId() {
    Set<String> existingIds = snippetRepository.findAllIds();

    String id;
    do {
      id = stringGenerator.generateString(idSymbols, idLength);
    } while (existingIds.contains(id));

    return id;
  }

}
