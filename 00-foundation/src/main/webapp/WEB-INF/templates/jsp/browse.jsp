<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:main-template>
  <jsp:attribute name="pageName">Browse Snippets</jsp:attribute>

  <jsp:body>
    <ul>
      <c:forEach var="snippet" items="${snippets}">
        <li>
          ${snippet.content}
        </li>
      </c:forEach>
    </ul>
  </jsp:body>
</t:main-template>

