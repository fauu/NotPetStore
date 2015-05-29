<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="util" uri="http://github.com/fauu/nps/jsp/tags/util" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message var="untitled" code="untitled" />
<c:set var="title" value="${not empty snippet.title ? snippet.title : untitled}" />

<t:mainTemplate>
  <jsp:attribute name="siteTitle"><c:out value="${title}" /></jsp:attribute>

  <jsp:attribute name="extraStylesheetDefs">
    <c:url var="prismStylesheetUrl" value="/public/third-party/css/prism.css" />
    <link rel="stylesheet" href="${prismStylesheetUrl}">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  </jsp:attribute>

  <jsp:body>
    <h2 id="view-header"><c:out value="${title}" /></h2>

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
    <spring:message var="anonymous" code="user.anonymous" />
    <spring:message code="addedByXOn" arguments="Anonymous" />
    <time class="with-help" datetime="${snippet.dateTimeAdded}" title="${formattedDateTimeAdded}"><c:out value="${formattedDateTimeAddedHumanized}" /></time>
    <span class="separator"></span> <spring:message code="numViews" />: <c:out value="${snippet.numViews}" />

    <main id="view-main">
      <div id="snippet-options-container">
        <ul class="snippet-options">
          <li>
            <c:url var="rawUrl" value="${snippetId}/raw" />
            <a href="${rawUrl}">
              <i class="fa fa-file-text-o fix-valign"></i>
              <span><spring:message code="snippetOptions.viewRaw" /></span>
            </a>
          </li>
          <li>
            <c:url var="downloadUrl" value="${snippetId}/download" />
            <a href="${downloadUrl}"><i class="fa fa-download"></i> <span>Download</span></a>
          </li>
        </ul>

        <c:if test="${not empty snippet.ownerPassword}">
          <c:url var="actionPath" value="${snippetId}" />
          <form action="${actionPath}" method="POST" id="snippet-owner-action-form">
            <ul class="snippet-options owner">
              <li id="owner-password-field">
                <label for="owner-password">
                  <spring:message code="snippet.ownerPassword" />:
                </label>
                <input type="password" name="ownerPassword" id="owner-password"
                       class="${userActionFeedback == 'SNIPPET_PERFORM_OWNER_ACTION_PASSWORD_INVALID' ? 'error' : ''}"/>
                <i class="fa fa-long-arrow-right"></i>
              </li>
              <li>
                <button type="submit" name="delete"><i class="fa fa-times fix-valign"></i> <span>Delete</span></button>
              </li>
            </ul>
          </form>
        </c:if>
      </div>
      <pre id="snippet-content"><code class="line-numbers language-${snippet.syntaxHighlighting.code}"><c:out value="${snippet.content}" /></code></pre>
    </main>

    <c:url var="prismJsUrl" value="/public/third-party/js/prism.js" />
    <script src="${prismJsUrl}"></script>
  </jsp:body>
</t:mainTemplate>

