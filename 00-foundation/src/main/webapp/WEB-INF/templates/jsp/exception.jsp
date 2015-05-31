<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:mainTemplate>
  <jsp:attribute name="pageTitle">
    <spring:message code="exceptionFeedback.ofStatusCode.${exceptionFeedback.statusCode}" />
  </jsp:attribute>

  <jsp:body>
    <div class="exception-message-box ${exceptionFeedback.severe ? 'severe' : ''}">
      <strong class="status-code">
        <c:out value="${exceptionFeedback.statusCode}" />
      </strong>

      <spring:message code="exceptionFeedback.${exceptionFeedback.code}" />
    </div>
  </jsp:body>
</t:mainTemplate>
