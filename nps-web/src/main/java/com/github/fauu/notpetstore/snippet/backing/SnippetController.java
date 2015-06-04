package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.PageRequest;
import com.github.fauu.notpetstore.common.feedback.ExceptionFeedback;
import com.github.fauu.notpetstore.common.feedback.UserActionFeedback;
import com.github.fauu.notpetstore.snippet.RequestedSnippetDeletedException;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetForm;
import com.github.fauu.notpetstore.snippet.SnippetSortType;
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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/")
class SnippetController {

  // TODO: Externalize this?
  private static final int SNIPPET_PAGE_SIZE = 10;

  @Autowired
  private SnippetService snippetService;

  @Autowired
  private CookieGenerator visitorIdCookieGenerator;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

    binder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

	@RequestMapping(method = GET, value = "/")
	public String add(Model model) throws Exception {
    model.addAttribute(new SnippetForm());

		return "add";
	}

  @RequestMapping(method = POST, value = "/")
  public String doAdd(@ModelAttribute @Valid SnippetForm snippetForm,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("userActionFeedback",
          UserActionFeedback.SNIPPET_ADD_FORM_INVALLID);

      return "add";
    }

    Snippet addedSnippet = snippetService.addSnippet(snippetForm);

    redirectAttributes.addAttribute("snippetId", addedSnippet.getId())
                      .addFlashAttribute("userActionFeedback",
                          UserActionFeedback.SNIPPET_ADD_SUCCESS);

    return "redirect:/{snippetId}";
  }

  @RequestMapping(method = GET, value = "/browse")
  public String browse() {
    return "redirect:/browse/page/1";
  }

  @RequestMapping(method = GET, value = "/browse/page/{pageNo}")
  public String browsePage(@PathVariable int pageNo,
                           @RequestParam(required = false) String sort,
                           @RequestParam(value = "syntax", required = false)
                               String syntaxHighlightingFilterCode,
                           Model model) {

    Optional<Snippet.SyntaxHighlighting> syntaxHighlightingFilter =
        Snippet.SyntaxHighlighting.fromCode(syntaxHighlightingFilterCode);

    model.addAttribute("snippetPage",
        snippetService.getListableSnippets(
            new PageRequest(pageNo, SNIPPET_PAGE_SIZE),
            SnippetSortType.fromCode(sort),
            syntaxHighlightingFilter));

    model.addAttribute("filteredSyntaxName",
        syntaxHighlightingFilter.map(Snippet.SyntaxHighlighting::getDisplayName)
                                .orElse(null));

    return "browse";
  }

  @RequestMapping(method = GET, value = "/{snippetId}")
  public String view(@PathVariable String snippetId,
                     @CookieValue(value = "npsVisitorId", required = false)
                        String visitorId,
                     Model model,
                     HttpServletResponse response) {
    Snippet snippet = snippetService.getNonDeletedSnippetById(snippetId);

    Optional<String> optionalNewVisitorId =
        snippetService
            .recordSnippetVisit(snippet, Optional.ofNullable(visitorId));

    if (optionalNewVisitorId.isPresent()) {
      visitorIdCookieGenerator.addCookie(response, optionalNewVisitorId.get());
    }

    model.addAttribute(snippet);

    return "view";
  }

  @RequestMapping(method = GET,
                  value = "/{snippetId}/raw",
                  produces = MediaType.TEXT_PLAIN_VALUE)
  public @ResponseBody String viewRaw(@PathVariable String snippetId) {
    return snippetService.getNonDeletedSnippetById(snippetId).getContent();
  }

  @RequestMapping(method = GET, value = "/{snippetId}/download")
  public void download(@PathVariable String snippetId,
                       HttpServletResponse response) throws IOException {
    Snippet snippet = snippetService.getNonDeletedSnippetById(snippetId);

    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
    response.setHeader("Content-Disposition",
                       "attachment;filename=" + snippet.getFilename() + ".txt");

    ServletOutputStream out = response.getOutputStream();

    out.print(snippet.getContent());
    out.flush();

    out.close();
  }

  @RequestMapping(method = POST,
                  value = "/{snippetId}",
                  params = {"delete", "ownerPassword"})
  public String doDelete(@PathVariable String snippetId,
                         @RequestParam String ownerPassword,
                         RedirectAttributes redirectAttributes) {
    Snippet snippet = snippetService.getNonDeletedSnippetById(snippetId);

    if (ownerPassword == null ||
        !snippetService.verifySnippetOwnerPassword(snippet, ownerPassword)) {
      redirectAttributes
          .addAttribute(snippetId)
          .addFlashAttribute("userActionFeedback",
              UserActionFeedback.SNIPPET_PERFORM_OWNER_ACTION_PASSWORD_INVALID);

      return "redirect:/{snippetId}";
    }

    snippetService.deleteSnippet(snippet);

    redirectAttributes.addFlashAttribute("userActionFeedback",
        UserActionFeedback.SNIPPET_DELETE_SUCCESS);

    return "redirect:/browse";
  }

  @RequestMapping(method = GET, value = "/fork/{snippetId}")
  public String fork(@PathVariable String snippetId, Model model) {
    Snippet snippet = snippetService.getNonDeletedSnippetById(snippetId);

    SnippetForm snippetForm = new SnippetForm(snippet, true);

    model.addAttribute(snippetForm);

    return "add";
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