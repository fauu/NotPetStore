package com.github.fauu.notpetstore.web.controller;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.service.PastingService;
import com.github.fauu.notpetstore.web.exception.RequestedSnippetDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class SnippetController {

  @Autowired
  private PastingService pastingService;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

    binder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String add(Model model) {
    model.addAttribute(new SnippetForm());

		return "add";
	}

  @RequestMapping(method = RequestMethod.POST, value = "/")
  public String doAdd(@ModelAttribute @Valid SnippetForm snippetForm,
                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "add";
    }

    pastingService.addSnippet(snippetForm);

    return "redirect:/";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/browse")
  public String browse(Model model) {
    model.addAttribute("snippets",
        pastingService.getNonDeletedPublicSnippetsSortedByDateTimeAddedDesc());

    return "browse";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{snippetId}")
  public String view(@PathVariable String snippetId, Model model) {
    model.addAttribute(pastingService.getNonDeletedSnippetById(snippetId));

    return "view";
  }

  @RequestMapping(method = RequestMethod.GET,
                  value = "/{snippetId}/raw",
                  produces = MediaType.TEXT_PLAIN_VALUE)
  public @ResponseBody String viewRaw(@PathVariable String snippetId) {
    return pastingService.getNonDeletedSnippetById(snippetId).getContent();
  }

  // TODO: Generate filename from snippet's title (if present)
  @RequestMapping(method = RequestMethod.GET, value = "/{snippetId}/download")
  public void download(@PathVariable String snippetId,
                       HttpServletResponse response) throws IOException {
    Snippet snippet = pastingService.getNonDeletedSnippetById(snippetId);

    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
    response.setHeader("Content-Disposition",
                       "attachment;filename=" + snippetId + ".txt");

    ServletOutputStream out = response.getOutputStream();

    out.print(snippet.getContent());
    out.flush();

    out.close();
  }

  @ExceptionHandler(RequestedSnippetDeletedException.class)
  public @ResponseStatus(HttpStatus.NOT_FOUND) ModelAndView deleted() {
    ModelAndView mav = new ModelAndView();

    mav.addObject("deletedSnippet", true);

    mav.setViewName("error/404");

    return mav;
  }

}