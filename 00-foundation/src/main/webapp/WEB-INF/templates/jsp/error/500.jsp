<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:mainTemplate>
  <jsp:attribute name="pageName">
    <spring:message code="serverError" />
  </jsp:attribute>

  <jsp:body>
    <div class="message error severe">
      <strong class="code">500</strong>
      <spring:message code="errorProcessingRequest" />
    </div>
  </jsp:body>
</t:mainTemplate>
