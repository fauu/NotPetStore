package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
class SnippetFormAdapter {

  private @Autowired SnippetUniqueIdGenerator snippetUniqueIdGenerator;

  private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

  public Snippet createSnippetFromSnippetForm(SnippetForm form) {
    LocalDateTime now = LocalDateTime.now();

    Snippet snippet = new Snippet(snippetUniqueIdGenerator.generateUniqueId(),
                                  form.getContent(),
                                  now);

    snippet.setTitle(form.getTitle());

    snippet.setSyntaxHighlighting(form.getSyntaxHighlighting());

    Optional<Duration> timeUntilExpiration =
        form.getExpirationMoment().getTimeUntil();
    snippet.setExpiresAt(timeUntilExpiration.map(now::plus).orElse(null));

    String ownerPassword = form.getOwnerPassword();
    if (ownerPassword != null) {
      String encoded = bCryptPasswordEncoder.encode(ownerPassword);
      snippet.setOwnerPassword(encoded);
    }

    snippet.setVisibility(form.getVisibility());

    return snippet;
  }

}
