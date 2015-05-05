package com.github.fauu.notpetstore.web.controller;

import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.service.PastingService;
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
  private PastingService pastingService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String add(Model model) {
    model.addAttribute(new SnippetForm());

		return "add";
	}

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String doAdd(@ModelAttribute @Valid SnippetForm snippetForm,
                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "add";
    }

    pastingService.addSnippet(snippetForm);

    return "redirect:/";
  }

  @RequestMapping(value = "/browse", method = RequestMethod.GET)
  public String browse(Model model) {
    model.addAttribute("snippets", pastingService.getNotDeletedPublicSnippetsSortedByDateTimeAddedDesc());

    return "browse";
  }

  @RequestMapping(value = "/{snippetId}", method = RequestMethod.GET)
  public String view(@PathVariable String snippetId) {
    throw new ResourceNotFoundException();
  }

}