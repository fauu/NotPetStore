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
    <h2><s:message code="snippets" /></h2>

    <table id="snippet-table">
      <thead>
        <tr>
          <th class="snippet-title">
            <s:message code="title" />
          <th class="snippet-added">
            <s:message code="addedDateTime" />
          <th class="snippet-views ${param.sort == "popular" ? 'is-sorted-by' : ''}">
            <s:url var="numViewsSortUrl" value="/browse/page/${snippetPage.no}">
              <c:if test="${empty param.sort}">
                <s:param name="sort" value="popular" />
              </c:if>
            </s:url>
            <a class="table-sort-link" href="${numViewsSortUrl}">
              <s:message code="numViews" />

              <i class="table-sort-icon material-icons">keyboard_arrow_down</i>
            </a>
      <tbody>
        <c:forEach var="snippet" items="${snippetPage.items}">
          <tr>
            <td class="snippet-title">
              <c:url var="snippetUrl" value="/${snippet.id}" />
              <s:message var="untitled" code="untitled" />
              <a href="${snippetUrl}">${not empty snippet.title ? snippet.title : untitled}</a>
            <td class="snippet-added">
              <u:relativeLocalDateTime localDateTime="${snippet.dateTimeAdded}" locale="${pageContext.response.locale}" />
            <td class="snippet-views">
              <c:out value="${snippet.numViews}" />
        </c:forEach>
    </table>

    <c:if test="${snippetPage.moreAvailable}">
      <s:url var="pageUrlTemplate" value="/browse/page/{pageNo}">
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
            <a class="page-nav-link first" href="${firstPageUrl}">
              <i class="page-nav-icon material-icons">fast_rewind</i> First
            </a>

            <s:url var="previousPageUrl" value="${pageUrlTemplate}">
              <s:param name="pageNo" value="${snippetPage.previousNo}" />
            </s:url>
            <a class="page-nav-link previous" href="${previousPageUrl}">
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
            <a class="page-nav-link next" href="${nextPageUrl}">
              Next <i class="page-nav-icon material-icons">keyboard_arrow_right</i>
            </a>

            <s:url var="lastPageUrl" value="${pageUrlTemplate}">
              <s:param name="pageNo" value="${snippetPage.lastNo}" />
            </s:url>
            <a class="page-nav-link last" href="${lastPageUrl}">
              Last <i class="page-nav-icon material-icons">fast_forward</i>
            </a>
          </c:if>
        </div>
      </nav>
    </c:if>
  </jsp:body>
</t:mainTemplate>

