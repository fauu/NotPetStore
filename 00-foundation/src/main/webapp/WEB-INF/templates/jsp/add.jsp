<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:main-template>
  <jsp:attribute name="pageName">Add New Snippet</jsp:attribute>

  <jsp:body>
    <jsp:include page="fragments/errorMessage.jsp" />

    <form:form action="/" commandName="snippet" method="POST">
      <form:textarea path="content" placeholder="Paste something!" />
      <form:button type="submit">Save</form:button>
    </form:form>
  </jsp:body>
</t:main-template>

