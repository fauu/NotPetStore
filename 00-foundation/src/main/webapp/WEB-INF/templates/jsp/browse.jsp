<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="util" uri="http://github.com/fauu/nps/jsp/tags/util" %>

<t:main-template>
  <jsp:attribute name="pageName"><spring:message code="browseSnippets" /></jsp:attribute>

  <jsp:body>
    <h2><spring:message code="snippets" /></h2>

    <table id="snippet-list">
      <thead>
        <tr>
          <th class="title">Title</th>
          <th class="added">Added</th>
          <th class="views">Views</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="snippet" items="${snippets}">
          <tr>
            <td class="title"><a href="<c:url value="/${snippet.id}" />">${snippet.title}</a></td>
            <td class="added">
              <util:relativeLocalDateTime localDateTime="${snippet.dateTimeAdded}" locale="en_US" />
            </td>
            <td class="views">${snippet.numViews}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </jsp:body>
</t:main-template>

