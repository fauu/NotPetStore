<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="util" uri="http://github.com/fauu/nps/jsp/tags/util" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:main-template>
  <jsp:attribute name="pageName"><spring:message code="browseSnippets" /></jsp:attribute>

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
        <c:forEach var="snippet" items="${snippets}">
          <tr>
            <td class="title">
              <c:url var="snippetUrl" value="/${snippet.id}" />
              <spring:message var="untitled" code="untitled" />
              <a href="${snippetUrl}">${not empty snippet.title ? snippet.title : untitled}</a>
            </td>

            <fmt:formatDate var="formattedDateTimeAdded"
                            value="${util:localDateTimeToDate(snippet.dateTimeAdded)}"
                            pattern="yyyy-MM-dd HH:mm:ss 'UTC'"
                            timeZone="UTC"/>
            <td class="added" title="${formattedDateTimeAdded}">
              <util:relativeLocalDateTime localDateTime="${snippet.dateTimeAdded}" locale="${pageContext.response.locale}" />
            </td>

            <td class="views">
              ${snippet.numViews}
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </jsp:body>
</t:main-template>

