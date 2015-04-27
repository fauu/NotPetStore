package com.github.fauu.notpetstore.web.controller;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.service.SnippetService;
import com.github.fauu.notpetstore.service.SnippetServiceImpl;
import com.github.fauu.notpetstore.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class SnippetController {

  private static final Logger logger
      = LoggerFactory.getLogger(SnippetController.class);

  @Autowired
  private SnippetService snippetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String add(final Model model) {
    final Snippet snippet = new Snippet();

    model.addAttribute(snippet);

		return "add";
	}

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String doAdd(final @ModelAttribute("snippet") Snippet snippet,
                      final RedirectAttributes redirectAttributes) {
    try {
      snippetService.add(snippet);
    } catch (ServiceException e) {
      logAndFlashError(e, redirectAttributes);
    }

    return "redirect:/";
  }

  @RequestMapping(value = "/browse", method = RequestMethod.GET)
  public String browse(final Model model,
                       final RedirectAttributes redirectAttributes) {
    try {
      final List<Snippet> snippets = snippetService.findAll();

      model.addAttribute("snippets", snippets);

      return "browse";
    } catch (ServiceException e) {
      logAndFlashError(e, redirectAttributes);

      return "redirect:/";
    }
  }

  @RequestMapping(value = "/{pasteId}", method = RequestMethod.GET)
  public String view(final @PathVariable("pasteId") String pasteId) {
    return "404";
  }

  private void logAndFlashError(final Exception e,
                                final RedirectAttributes redirectAttributes) {
    logger.error("", e);

    redirectAttributes.addFlashAttribute("error",
        "There was an error processing your request. Please try again.");
  }

}