<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:mainTemplate>
  <jsp:attribute name="pageName">
    <spring:message code="notFound" />
  </jsp:attribute>

  <jsp:body>
    <div class="message error">
      <strong class="code">404</strong>

      <spring:message var="snippetHasBeenDeleted" code="snippetHasBeenDeleted" />
      <spring:message var="requestedPageNotFound" code="requestedPageNotFound" />
      <c:out value="${deletedSnippet ? snippetHasBeenDeleted :
                                       requestedPageNotFound}" />
    </div>
  </jsp:body>
</t:mainTemplate>
