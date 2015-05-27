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

    <nav role="page">
      <ul>
        <c:forEach begin="1" end="${snippetPage.numPagesTotal}" varStatus="loop">
          <li>
            <c:url var="pageUrl" value="/browse/page/${loop.index}" />
            <a href="${pageUrl}">
              <c:out value="${loop.index}" />
            </a>
          </li>
        </c:forEach>
      </ul>
    </nav>
  </jsp:body>
</t:mainTemplate>

