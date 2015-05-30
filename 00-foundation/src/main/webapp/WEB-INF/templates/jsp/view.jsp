<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="util" uri="http://github.com/fauu/nps/jsp/tags/util" %>

<spring:message var="untitled" code="untitled" />
<c:set var="snippetTitle" value="${not empty snippet.title ? snippet.title : untitled}" />

<t:mainTemplate>

  <jsp:attribute name="pageTitle">
    <c:out value="${snippetTitle}" />
  </jsp:attribute>

  <jsp:attribute name="extraStylesheetDefs">
    <c:url var="prismStylesheetUrl" value="/public/third-party/css/prism.css" />
    <link rel="stylesheet" href="${prismStylesheetUrl}">
  </jsp:attribute>

  <jsp:body>

    <h2 class="with-page-details">
      <c:out value="${snippetTitle}" />
    </h2>

    <c:set var="dateTimeAddedAsDate" value="${util:localDateTimeToDate(snippet.dateTimeAdded)}" />
    <fmt:setLocale value="${pageContext.response.locale}" scope="session"/>
    <fmt:formatDate var="formattedDateTimeAdded"
                    value="${dateTimeAddedAsDate}"
                    pattern="yyyy-MM-dd HH:mm:ss 'UTC'"
                    timeZone="UTC" />
    <fmt:formatDate var="formattedDateTimeAddedHumanized"
                    value="${dateTimeAddedAsDate}"
                    dateStyle="long"
                    timeZone="UTC" />
    <div class="page-details">

      <spring:message var="anonymous" code="user.anonymous" />
      <spring:message code="addedByXOn" arguments="Anonymous" />

      <time class="with-help" datetime="${snippet.dateTimeAdded}" title="${formattedDateTimeAdded}">
        <c:out value="${formattedDateTimeAddedHumanized}" />
      </time>

      <span class="separator"></span> <spring:message code="numViews" />: <c:out value="${snippet.numViews}" />

    </div>

    <div class="text-card">

      <div class="text-card-options-box">

        <ul class="link-list">

          <li class="text-card-option link-list-element">

            <c:url var="rawUrl" value="${snippetId}/raw" />
            <a href="${rawUrl}">

              <i class="material-icons fix-valign">open_in_new</i>

              <span><spring:message code="snippetOptions.viewRaw" /></span>

            </a>

          <li class="text-card-option link-list-element">

            <c:url var="downloadUrl" value="${snippetId}/download" />
            <a href="${downloadUrl}">

              <i class="material-icons">file_download</i>

              <span><spring:message code="snippetOptions.download" /></span>

            </a>

        </ul>

        <c:if test="${not empty snippet.ownerPassword}">

          <c:url var="actionPath" value="${snippetId}" />
          <form id="snippet-owner-action-form" class="inline-form" method="POST" action="${actionPath}">

            <label class="field-label-inline" for="snippet-owner-password-field">
              <spring:message code="snippet.ownerPassword" />:
            </label>

            <c:set var="ownerPasswordFieldStateClass"
                   value="${userActionFeedback == 'SNIPPET_PERFORM_OWNER_ACTION_PASSWORD_INVALID' ? 'is-invalid' : ''}" />
            <input id="snippet-owner-password-field" class="${ownerPasswordFieldStateClass}" type="password" name="ownerPassword" />

            <i class="flow-direction-indicator material-icons">forward</i>

            <ul class="link-list">

              <li class="link-list-element">

                <button id="snippet-delete-button" type="submit" name="delete">

                  <i class="material-icons fix-valign">delete</i>

                  <spring:message code="form.delete" />

                </button>

            </ul>

          </form>

        </c:if>

      </div>

      <pre class="text-card-content"><code class="line-numbers language-${snippet.syntaxHighlighting.code}"><c:out value="${snippet.content}" /></code></pre>

    </div>

    <c:url var="prismJsUrl" value="/public/third-party/js/prism.js" />
    <script src="${prismJsUrl}"></script>

  </jsp:body>

</t:mainTemplate>

