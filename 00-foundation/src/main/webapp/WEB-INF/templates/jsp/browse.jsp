<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="util" uri="http://github.com/fauu/nps/jsp/tags/util" %>

<t:mainTemplate>
  <jsp:attribute name="pageTitle"><spring:message code="browseSnippets" /></jsp:attribute>

  <jsp:body>
    <h2><spring:message code="snippets" /></h2>

    <table id="snippet-list">
      <thead>
        <tr>
          <th class="title">
            <spring:message code="title" />
          </th>
          <th class="added">
            <spring:message code="addedDateTime" />
          </th>
          <th class="views">
            <spring:message code="numViews" />
          </th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="snippet" items="${snippetPage.items}">
          <tr>
            <td class="title">
              <c:url var="snippetUrl" value="/${snippet.id}" />
              <spring:message var="untitled" code="untitled" />
              <a href="${snippetUrl}">${not empty snippet.title ? snippet.title : untitled}</a>
            </td>

            <td class="added">
              <util:relativeLocalDateTime localDateTime="${snippet.dateTimeAdded}" locale="${pageContext.response.locale}" />
            </td>

            <td class="views">
              ${snippet.numViews}
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <c:if test="${snippetPage.numPagesTotal > 1}">
      <nav id="page-nav">
        <c:url var="pageUrlRoot" value="/browse/page/" />

        <div class="previous-links">
          <c:if test="${snippetPage.no > 1}">
            <a href="${pageUrlRoot}1" class="first">
              <i class="fa fa-backward"></i> First
            </a>

            <a href="${pageUrlRoot}${snippetPage.no - 1}" class="previous">
              <i class="fa fa-chevron-left"></i> Previous
            </a>
          </c:if>
        </div>

        <div class="current">
          <span class="page-no">
            <c:out value="Page ${snippetPage.no}" />
          </span>
          <span class="page-count">
            <c:out value="of ${snippetPage.numPagesTotal}" />
          </span>
        </div>

        <div class="next-links">
          <c:if test="${snippetPage.no < snippetPage.numPagesTotal}">
            <a href="${pageUrlRoot}${snippetPage.no + 1}" class="next">
              Next <i class="fa fa-chevron-right"></i>
            </a>

            <a href="${pageUrlRoot}${snippetPage.numPagesTotal}" class="last">
              Last <i class="fa fa-forward"></i>
            </a>
          </c:if>
        </div>
      </nav>
    </c:if>
  </jsp:body>
</t:mainTemplate>

