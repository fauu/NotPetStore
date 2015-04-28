package com.github.fauu.notpetstore.web.controller;

import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.service.SnippetService;
import com.github.fauu.notpetstore.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class SnippetController {

  @Autowired
  private SnippetService snippetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String add(final Model model) {
    model.addAttribute(new SnippetForm());

		return "add";
	}

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String doAdd(final @ModelAttribute @Valid SnippetForm snippetForm,
                      final BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "add";
    }

    snippetService.add(snippetForm);

    return "redirect:/";
  }

  @RequestMapping(value = "/browse", method = RequestMethod.GET)
  public String browse(final Model model) {
    model.addAttribute("snippets", snippetService.findAll());

    return "browse";
  }

  @RequestMapping(value = "/{snippetId}", method = RequestMethod.GET)
  public String view(final @PathVariable String snippetId) {
    throw new ResourceNotFoundException();
  }

}