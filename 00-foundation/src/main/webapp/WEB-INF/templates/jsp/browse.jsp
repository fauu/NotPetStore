<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="u" uri="http://github.com/fauu/nps/jsp/tags/util" %>

<t:mainTemplate>
  <jsp:attribute name="pageTitle">
    <s:message code="browseSnippets" />
  </jsp:attribute>

  <jsp:body>
    <h2 class="${not empty filteredSyntaxName ? 'with-page-details' : ''}">
      <s:message code="snippets" />
    </h2>
    <c:if test="${not empty filteredSyntaxName}">
      <div class="page-details">
        <s:message code="withXSyntaxHighlighting" arguments="${filteredSyntaxName}" />

        <c:url var="browseAllUrl" value="/browse">
          <c:if test="${not empty param.sort}">
            <c:param name="sort" value="${param.sort}" />
          </c:if>
        </c:url>
        <a href="${browseAllUrl}">Show all</a>
      </div>
    </c:if>

    <table id="snippet-table">
      <thead>
        <tr>
          <th class="snippet-title">
            <s:message code="title" />
          <th class="snippet-syntax">
            <s:message code="syntax" />
          <th class="snippet-added">
            <s:message code="addedDateTime" />
          <th class="snippet-views ${param.sort == 'popular' ? 'is-sorted-by' : ''}">
            <c:url var="numViewsSortUrl" value="/browse/page/${snippetPage.no}">
              <c:if test="${not empty param.syntax}">
                <c:param name="syntax" value="${param.syntax}" />
              </c:if>
              <c:if test="${empty param.sort}">
                <c:param name="sort" value="popular" />
              </c:if>
            </c:url>
            <a class="table-sort-link" href="${numViewsSortUrl}">
              <i class="table-sort-icon material-icons">keyboard_arrow_down</i>

              <s:message code="numViews" />
            </a>
      <tbody>
        <c:forEach var="snippet" items="${snippetPage.items}">
          <tr>
            <td class="snippet-title">
              <c:url var="snippetUrl" value="/${snippet.id}" />
              <s:message var="untitled" code="untitled" />
              <c:set var="snippetTitle" value="${not empty snippet.title ? snippet.title : untitled}" />
              <a href="${snippetUrl}"><c:out value="${snippetTitle}" /></a>
            <td class="snippet-syntax">
              <c:url var="syntaxHighlightingFilterUrl" value="/browse/page/1">
                <c:param name="syntax" value="${snippet.syntaxHighlighting.code}" />
                <c:if test="${not empty param.sort}">
                  <c:param name="sort" value="${param.sort}" />
                </c:if>
              </c:url>
              <a class="secondary-link" href="${syntaxHighlightingFilterUrl}">
                <c:out value="${snippet.syntaxHighlighting}" />
              </a>
            <td class="snippet-added">
              <u:relativeLocalDateTime localDateTime="${snippet.dateTimeAdded}" locale="${pageContext.response.locale}" />
            <td class="snippet-views">
              <c:out value="${snippet.numViews}" />
        </c:forEach>
    </table>

    <c:if test="${snippetPage.moreAvailable}">
      <s:url var="pageUrlTemplate" value="/browse/page/{pageNo}">
        <c:if test="${not empty param.syntax}">
          <s:param name="syntax" value="${param.syntax}" />
        </c:if>
        <c:if test="${not empty param.sort}">
          <s:param name="sort" value="${param.sort}" />
        </c:if>
      </s:url>

      <nav id="page-nav">
        <div class="page-nav-element back-links">
          <c:if test="${snippetPage.previousAvailable}">
            <s:url var="firstPageUrl" value="${pageUrlTemplate}">
              <s:param name="pageNo" value="${snippetPage.firstNo}" />
            </s:url>
            <a class="secondary-link first" href="${firstPageUrl}">
              <i class="page-nav-icon material-icons">fast_rewind</i> First
            </a>

            <s:url var="previousPageUrl" value="${pageUrlTemplate}">
              <s:param name="pageNo" value="${snippetPage.previousNo}" />
            </s:url>
            <a class="secondary-link previous" href="${previousPageUrl}">
              <i class="page-nav-icon material-icons">keyboard_arrow_left</i> Previous
            </a>
          </c:if>
        </div>

        <div class="page-nav-element current-label">
          <span class="current-page-no">
            <c:out value="Page ${snippetPage.no}" />
          </span>

          <span class="page-count">
            <c:out value="of ${snippetPage.numPagesTotal}" />
          </span>
        </div>

        <div class="page-nav-element forward-links">
          <c:if test="${snippetPage.nextAvailable}">
            <s:url var="nextPageUrl" value="${pageUrlTemplate}">
              <s:param name="pageNo" value="${snippetPage.nextNo}" />
            </s:url>
            <a class="secondary-link next" href="${nextPageUrl}">
              Next <i class="page-nav-icon material-icons">keyboard_arrow_right</i>
            </a>

            <s:url var="lastPageUrl" value="${pageUrlTemplate}">
              <s:param name="pageNo" value="${snippetPage.lastNo}" />
            </s:url>
            <a class="secondary-link last" href="${lastPageUrl}">
              Last <i class="page-nav-icon material-icons">fast_forward</i>
            </a>
          </c:if>
        </div>
      </nav>
    </c:if>
  </jsp:body>
</t:mainTemplate>

