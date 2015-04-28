<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:main-template>
  <jsp:attribute name="pageName">Add New Snippet</jsp:attribute>

  <jsp:body>
    <form:form action="/" commandName="snippetForm" method="POST">
      <form:input path="title" placeholder="Snippet title" />

      <spring:bind path="content">
        <form:textarea path="content" placeholder="Paste something!" class="${status.error ? 'error' : ''}" />
      </spring:bind>

      <form:button type="submit">Save</form:button>
    </form:form>
  </jsp:body>
</t:main-template>

