package com.github.fauu.notpetstore.web.controller;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.service.PastingService;
import com.github.fauu.notpetstore.service.SnippetVisitRecordingService;
import com.github.fauu.notpetstore.service.exception.RequestedSnippetDeletedException;
import com.github.fauu.notpetstore.web.feedback.ExceptionFeedback;
import com.github.fauu.notpetstore.web.feedback.UserActionFeedback;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class SnippetController {

  // TODO: Externalize this?
  private static final int SNIPPET_PAGE_SIZE = 10;

  @Autowired
  private PastingService pastingService;

  @Autowired
  private SnippetVisitRecordingService snippetVisitRecordingService;

  @Autowired
  private CookieGenerator visitorIdCookieGenerator;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

    binder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String add(Model model) throws Exception {
    model.addAttribute(new SnippetForm());

		return "add";
	}

  @RequestMapping(method = RequestMethod.POST, value = "/")
  public String doAdd(@ModelAttribute @Valid SnippetForm snippetForm,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("userActionFeedback",
          UserActionFeedback.SNIPPET_ADD_FORM_INVALLID);

      return "add";
    }

    pastingService.addSnippet(snippetForm);

    redirectAttributes.addFlashAttribute("userActionFeedback",
        UserActionFeedback.SNIPPET_ADD_SUCCESS);

    return "redirect:/browse";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/browse")
  public String browse() {
    return "forward:/browse/page/1";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/browse/page/{pageNo}")
  public String browsePage(@PathVariable int pageNo, Model model) {
    model.addAttribute("snippetPage",
        pastingService.getPageOfNonDeletedPublicSnippets(pageNo, SNIPPET_PAGE_SIZE));

    return "browse";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{snippetId}")
  public String view(@PathVariable String snippetId,
                     @CookieValue(value = "npsVisitorId", required = false)
                        String visitorId,
                     Model model,
                     HttpServletResponse response) {
    Snippet snippet = pastingService.getNonDeletedSnippetById(snippetId);

    Optional<String> optionalNewVisitorId =
        snippetVisitRecordingService
            .recordSnippetVisit(snippet, Optional.ofNullable(visitorId));

    if (optionalNewVisitorId.isPresent()) {
      visitorIdCookieGenerator.addCookie(response, optionalNewVisitorId.get());
    }

    model.addAttribute(snippet);

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
                       "attachment;filename=" + snippet.getFilename());

    ServletOutputStream out = response.getOutputStream();

    out.print(snippet.getContent());
    out.flush();

    out.close();
  }

  @RequestMapping(method = RequestMethod.POST,
                  value = "/{snippetId}",
                  params = {"delete", "ownerPassword"})
  public String doDelete(@PathVariable String snippetId,
                         @RequestParam String ownerPassword,
                         RedirectAttributes redirectAttributes) {
    Snippet snippet = pastingService.getNonDeletedSnippetById(snippetId);

    if (ownerPassword == null ||
        !pastingService.verifySnippetOwnerPassword(snippet, ownerPassword)) {
      redirectAttributes
          .addAttribute(snippetId)
          .addFlashAttribute("userActionFeedback",
              UserActionFeedback.SNIPPET_PERFORM_OWNER_ACTION_PASSWORD_INVALID);

      return "redirect:/{snippetId}";
    }

    pastingService.deleteSnippet(snippet);

    redirectAttributes.addFlashAttribute("userActionFeedback",
        UserActionFeedback.SNIPPET_DELETE_SUCCESS);

    return "redirect:/browse";
  }

  @ExceptionHandler(RequestedSnippetDeletedException.class)
  public @ResponseStatus(HttpStatus.NOT_FOUND) ModelAndView deleted() {
    ModelAndView mav = new ModelAndView();

    mav.addObject("exceptionFeedback",
        ExceptionFeedback.REQUESTED_SNIPPET_DELETED);

    mav.setViewName("exception");

    return mav;
  }

}