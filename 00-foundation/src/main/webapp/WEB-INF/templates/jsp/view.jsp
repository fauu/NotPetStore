<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="util" uri="http://github.com/fauu/nps/jsp/tags/util" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message var="untitled" code="untitled" />
<c:set var="title" value="${not empty snippet.title ? snippet.title : untitled}" />

<t:mainTemplate>
  <jsp:attribute name="pageName">
    <c:out value="${title}" />
  </jsp:attribute>

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
        <ul id="snippet-options">
          <li>
            <c:url var="rawUrl" value="${snippetId}/raw" />
            <a href="${rawUrl}"><i class="fa fa-file-text-o fix-valign"></i> <span>View Raw</span></a>
          </li>
          <li>
            <c:url var="downloadUrl" value="${snippetId}/download" />
            <a href="${downloadUrl}"><i class="fa fa-download"></i> <span>Download</span></a>
          </li>
        </ul>
      </div>
      <pre id="snippet-content"><code class="line-numbers language-${snippet.syntaxHighlighting.code}"><c:out value="${snippet.content}" /></code></pre>
    </main>

    <c:url var="prismJsUrl" value="/public/third-party/js/prism.js" />
    <script src="${prismJsUrl}"></script>
  </jsp:body>
</t:mainTemplate>

