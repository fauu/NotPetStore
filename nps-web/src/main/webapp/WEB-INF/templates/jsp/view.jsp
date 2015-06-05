<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="u" uri="http://github.com/fauu/nps/jsp/tags/util" %>

<s:message var="untitled" code="untitled" />
<c:set var="snippetTitle" value="${not empty snippet.title ? snippet.title : untitled}" />

<t:mainTemplate>
  <jsp:attribute name="pageTitle">
    <c:out value="${snippetTitle}" />
  </jsp:attribute>

  <jsp:attribute name="extraStylesheetLinks">
    <c:url var="prismStylesheetUrl" value="/public/third-party/css/prism.css" />
    <link rel="stylesheet" href="${prismStylesheetUrl}">
  </jsp:attribute>

  <jsp:attribute name="extraScriptLinks">
    <c:url var="prismJsUrl" value="/public/third-party/js/prism.js" />
    <script src="${prismJsUrl}"></script>
  </jsp:attribute>

  <jsp:body>
    <h2 class="with-page-details">
      <c:out value="${snippetTitle}" />
    </h2>

    <c:set var="createdAtAsDate" value="${u:localDateTimeToDate(snippet.createdAt)}" />
    <f:setLocale value="${pageContext.response.locale}" scope="session"/>
    <f:formatDate var="formattedCreatedAt"
                    value="${createdAtAsDate}"
                    pattern="yyyy-MM-dd HH:mm:ss 'UTC'"
                    timeZone="UTC" />
    <f:formatDate var="formattedCreatedAtHumanized"
                    value="${createdAtAsDate}"
                    dateStyle="long"
                    timeZone="UTC" />
    <div class="page-details">
      <s:message var="anonymous" code="user.anonymous" />

      <s:message code="addedByXOn" arguments="Anonymous" />

      <time class="with-help" datetime="${snippet.createdAt}" title="${formattedCreatedAt}">
        <c:out value="${formattedCreatedAtHumanized}" />
      </time>

      <span class="separator"></span> <s:message code="viewCount" />: <c:out value="${snippet.viewCount}" />
    </div>

    <div class="text-card">
      <div class="text-card-options-box">
        <ul class="link-list">
          <li class="text-card-option link-list-element">
            <c:url var="rawUrl" value="${snippetId}/raw" />
            <a href="${rawUrl}">
              <i class="material-icons fix-valign">open_in_new</i>

              <span><s:message code="snippetOptions.viewRaw" /></span>
            </a>
          <li class="text-card-option link-list-element">
            <c:url var="downloadUrl" value="${snippetId}/download" />
            <a href="${downloadUrl}">
              <i class="material-icons">file_download</i>

              <span><s:message code="snippetOptions.download" /></span>
            </a>
          <li class="text-card-option link-list-element">
            <c:url var="forkUrl" value="fork/${snippetId}" />
            <a href="${forkUrl}">
              <i class="material-icons fix-valign">call_split</i>

              <span><s:message code="snippetOptions.fork" /></span>
            </a>
        </ul>

        <c:if test="${not empty snippet.ownerPassword}">
          <c:url var="actionPath" value="${snippetId}" />
          <form id="snippet-owner-action-form" class="inline-form" method="POST" action="${actionPath}">
            <label class="field-label-inline" for="snippet-owner-password-field">
              <s:message code="snippet.ownerPassword" />:
            </label>

            <c:set var="ownerPasswordFieldStateClass"
                   value="${userActionFeedback == 'SNIPPET_PERFORM_OWNER_ACTION_PASSWORD_INVALID' ? 'is-invalid' : ''}" />
            <input id="snippet-owner-password-field" class="${ownerPasswordFieldStateClass}" type="password" name="ownerPassword">

            <i class="flow-direction-indicator material-icons">forward</i>

            <ul class="link-list">
              <li class="link-list-element">
                <button id="snippet-delete-button" type="submit" name="delete">
                  <i class="material-icons fix-valign">delete</i>

                  <s:message code="form.delete" />
                </button>
            </ul>
          </form>
        </c:if>
      </div>

      <pre class="text-card-content"><code class="line-numbers language-${snippet.syntaxHighlighting.code}"><c:out value="${snippet.content}" /></code></pre>
    </div>
  </jsp:body>
</t:mainTemplate>

