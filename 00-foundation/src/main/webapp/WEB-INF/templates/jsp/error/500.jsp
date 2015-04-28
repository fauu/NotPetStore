<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:main-template>
  <jsp:attribute name="pageName">Server Error</jsp:attribute>

  <jsp:body>
    <div class="message error severe">
      <strong class="code">500</strong>There was an error processing your request. Please try again.
    </div>
  </jsp:body>
</t:main-template>
