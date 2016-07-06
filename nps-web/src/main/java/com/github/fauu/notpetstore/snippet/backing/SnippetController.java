package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.Page;
import com.github.fauu.notpetstore.common.PageRequest;
import com.github.fauu.notpetstore.common.ExceptionFeedback;
import com.github.fauu.notpetstore.common.UserActionFeedback;
import com.github.fauu.notpetstore.snippet.RequestedSnippetDeletedException;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetForm;
import com.github.fauu.notpetstore.snippet.SnippetSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/")
class SnippetController {

  private @Autowired SnippetService snippetService;

  private @Autowired CookieGenerator visitorIdCookieGenerator;

  @Value("${browseSnippets.pageSize}")
  private int snippetPageSize = 1;

  @Value("${visitorIdCookieName}")
  private String visitorIdCookieName;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

    binder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

	@RequestMapping(value = "/", method = {GET, HEAD})
	public String add(Model model) {
    model.addAttribute(new SnippetForm());

		return "add";
	}

  @RequestMapping(value = "/", method = POST)
  public String doAdd(@ModelAttribute @Valid SnippetForm snippetForm,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute(UserActionFeedback.SNIPPET_ADD_FORM_INVALLID);

      return "add";
    }

    Snippet addedSnippet = snippetService.addSnippet(snippetForm);

    redirectAttributes.addAttribute("snippetId", addedSnippet.getId())
                      .addFlashAttribute(UserActionFeedback.SNIPPET_ADD_SUCCESS);

    return "redirect:/{snippetId}";
  }

  @RequestMapping(value = "/browse", method = {GET, HEAD})
  public String browse() {
    return "redirect:/browse/page/1";
  }

  @RequestMapping(value = "/browse/page/{pageNo}", method = {GET, HEAD})
  public String browsePage(@PathVariable int pageNo,
                           @RequestParam(value = "sort", required = false)
                               String sortCode,
                           @RequestParam(value = "syntax", required = false)
                               String syntaxHighlightingCode,
                           Model model) {

    Optional<Snippet.SyntaxHighlighting> syntaxHighlightingFilter =
        Snippet.SyntaxHighlighting.ofCode(syntaxHighlightingCode);

    Page<Snippet> snippetPage =
        snippetService.getListableSnippets(
            new PageRequest(pageNo, snippetPageSize),
            SnippetSort.ofCode(sortCode).orElse(SnippetSort.RECENT_FIRST),
            syntaxHighlightingFilter);
    model.addAttribute("snippetPage", snippetPage);

    String filteredSyntaxName =
        syntaxHighlightingFilter.map(Snippet.SyntaxHighlighting::getDisplayName)
                                .orElse(null);
    model.addAttribute("filteredSyntaxName", filteredSyntaxName);

    return "browse";
  }

  @RequestMapping(value = "/{snippetId}", method = {GET, HEAD})
  public String view(@PathVariable String snippetId,
                     @CookieValue(value = "${visitorIdCookieName}",
                                  required = false)
                        String visitorId,
                     Model model,
                     HttpServletResponse response) {
    Snippet snippet = snippetService.getNonDeletedSnippetById(snippetId);

    Optional<String> newVisitorId =
        snippetService
            .recordSnippetVisit(snippet, Optional.ofNullable(visitorId));

    if (newVisitorId.isPresent()) {
      visitorIdCookieGenerator.addCookie(response, newVisitorId.get());
    }

    model.addAttribute(snippet);

    return "view";
  }

  @RequestMapping(value = "/{snippetId}/raw",
                  method = {GET, HEAD},
                  produces = MediaType.TEXT_PLAIN_VALUE)
  public @ResponseBody String viewRaw(@PathVariable String snippetId) {
    return snippetService.getNonDeletedSnippetById(snippetId).getContent();
  }

  @RequestMapping(value = "/{snippetId}/download", method = {GET, HEAD})
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

  @RequestMapping(value = "/{snippetId}",
                  method = POST,
                  params = {"delete", "ownerPassword"})
  public String doDelete(@PathVariable String snippetId,
                         @RequestParam String ownerPassword,
                         RedirectAttributes redirectAttributes) {
    Snippet snippet = snippetService.getNonDeletedSnippetById(snippetId);

    if (!snippetService.verifySnippetOwnerPassword(snippet, ownerPassword)) {
      redirectAttributes
          .addAttribute(snippetId)
          .addFlashAttribute(
              UserActionFeedback.SNIPPET_PERFORM_OWNER_ACTION_PASSWORD_INVALID);

      return "redirect:/{snippetId}";
    }

    snippetService.deleteSnippet(snippet);

    redirectAttributes
        .addFlashAttribute(UserActionFeedback.SNIPPET_DELETE_SUCCESS);

    return "redirect:/browse";
  }

  @RequestMapping(value = "/fork/{snippetId}", method = {GET, HEAD})
  public String fork(@PathVariable String snippetId, Model model) {
    Snippet snippet = snippetService.getNonDeletedSnippetById(snippetId);

    SnippetForm snippetForm = new SnippetForm(snippet, true);

    model.addAttribute(snippetForm);

    return "add";
  }

  @ExceptionHandler(RequestedSnippetDeletedException.class)
  public @ResponseStatus(HttpStatus.NOT_FOUND) ModelAndView deleted() {
    ModelAndView mav = new ModelAndView();

    mav.addObject(ExceptionFeedback.REQUESTED_SNIPPET_DELETED);

    mav.setViewName("exception");

    return mav;
  }

}