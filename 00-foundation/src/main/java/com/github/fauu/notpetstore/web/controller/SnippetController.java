package com.github.fauu.notpetstore.web.controller;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.service.SnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class SnippetController {

  @Autowired
  private SnippetService snippetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String add(Model model) {
    final Snippet snippet = new Snippet();

    model.addAttribute(snippet);

		return "add";
	}

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String doAdd(final @ModelAttribute("snippet") Snippet snippet) {
    snippetService.add(snippet);

    return "redirect:/browse";
  }

  @RequestMapping(value = "/browse", method = RequestMethod.GET)
  public String browse(final Model model) {
    final List<Snippet> snippets = snippetService.findAll();

    model.addAttribute("snippets", snippets);

    return "browse";
  }

  @RequestMapping(value = "/{pasteId}", method = RequestMethod.GET)
  public String view(final @PathVariable("pasteId") String pasteId) {
    return "add";
  }

}