package com.github.fauu.notpetstore.web.controller;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.service.PastingService;
import com.github.fauu.notpetstore.web.exception.RequestedSnippetDeletedException;
import com.github.fauu.notpetstore.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

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
    model.addAttribute("snippets",
        pastingService.getNonDeletedPublicSnippetsSortedByDateTimeAddedDesc());

    return "browse";
  }

//  @RequestMapping(value = "/{snippetId}/download",
//                  method = RequestMethod.GET,
//                  produces = "text/plain")
//  @ResponseBody
//  public String download(@PathVariable String snippetId) {
//    return pastingService.getNonDeletedSnippetById(snippetId).getContent();
//  }

  @RequestMapping(value = { "/{snippetId}", "/{snippetId}/{variant}"},
                  method = RequestMethod.GET)
  public String view(@PathVariable String snippetId,
                     @PathVariable("variant") Optional<String> variantOptional,
                     Model model) {
    model.addAttribute(pastingService.getNonDeletedSnippetById(snippetId));

    return variantOptional.filter(v -> v.equals("raw"))
                          .map(v -> "viewRaw")
                          .orElse("view");
  }

  @ExceptionHandler(RequestedSnippetDeletedException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView deleted() {
    ModelAndView mav = new ModelAndView();

    mav.addObject("deletedSnippet", true);

    mav.setViewName("error/404");

    return mav;
  }

}