<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="util" uri="http://github.com/fauu/nps/jsp/tags/util" %>

<t:mainTemplate>
  <jsp:attribute name="pageTitle">
    <spring:message code="browseSnippets" />
  </jsp:attribute>

  <jsp:body>
    <h2><spring:message code="snippets" /></h2>

    <table id="snippet-table">
      <thead>
        <tr>
          <th class="snippet-title">
            <spring:message code="title" />
          <th class="snippet-added">
            <spring:message code="addedDateTime" />
          <th class="snippet-views ${param.sort == "popular" ? 'is-sorted-by' : ''}">
            <spring:url var="numViewsSortUrl" value="/browse/page/${snippetPage.no}">
              <c:if test="${empty param.sort}">
                <spring:param name="sort" value="popular" />
              </c:if>
            </spring:url>
            <a class="table-sort-link" href="${numViewsSortUrl}">
              <spring:message code="numViews" />

              <i class="table-sort-icon material-icons">keyboard_arrow_down</i>
            </a>
      <tbody>
        <c:forEach var="snippet" items="${snippetPage.items}">
          <tr>
            <td class="snippet-title">
              <c:url var="snippetUrl" value="/${snippet.id}" />
              <spring:message var="untitled" code="untitled" />
              <a href="${snippetUrl}">${not empty snippet.title ? snippet.title : untitled}</a>
            <td class="snippet-added">
              <util:relativeLocalDateTime localDateTime="${snippet.dateTimeAdded}" locale="${pageContext.response.locale}" />
            <td class="snippet-views">
              <c:out value="${snippet.numViews}" />
        </c:forEach>
    </table>

    <c:if test="${snippetPage.moreAvailable}">
      <spring:url var="pageUrlTemplate" value="/browse/page/{pageNo}">
        <c:if test="${not empty param.sort}">
          <spring:param name="sort" value="${param.sort}" />
        </c:if>
      </spring:url>

      <nav id="page-nav">
        <div class="page-nav-element back-links">
          <c:if test="${snippetPage.previousAvailable}">
            <spring:url var="firstPageUrl" value="${pageUrlTemplate}">
              <spring:param name="pageNo" value="${snippetPage.firstNo}" />
            </spring:url>
            <a class="page-nav-link first" href="${firstPageUrl}">
              <i class="page-nav-icon material-icons">fast_rewind</i> First
            </a>

            <spring:url var="previousPageUrl" value="${pageUrlTemplate}">
              <spring:param name="pageNo" value="${snippetPage.previousNo}" />
            </spring:url>
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
            <spring:url var="nextPageUrl" value="${pageUrlTemplate}">
              <spring:param name="pageNo" value="${snippetPage.nextNo}" />
            </spring:url>
            <a class="page-nav-link next" href="${nextPageUrl}">
              Next <i class="page-nav-icon material-icons">keyboard_arrow_right</i>
            </a>

            <spring:url var="lastPageUrl" value="${pageUrlTemplate}">
              <spring:param name="pageNo" value="${snippetPage.lastNo}" />
            </spring:url>
            <a class="page-nav-link last" href="${lastPageUrl}">
              Last <i class="page-nav-icon material-icons">fast_forward</i>
            </a>
          </c:if>
        </div>
      </nav>
    </c:if>
  </jsp:body>
</t:mainTemplate>

