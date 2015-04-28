<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:main-template>
  <jsp:attribute name="pageName"><spring:message code="addNewSnippet" /></jsp:attribute>

  <jsp:body>
    <form:form action="/" commandName="snippetForm" method="POST">
      <div class="field">
        <spring:message code="snippetForm.titlePlaceholder" var="titlePlaceholder" />
        <spring:bind path="title">
          <form:input path="title" placeholder="${titlePlaceholder}" class="${status.error ? 'error' : ''}"/>
          <form:errors path="title" class="errors" />
        </spring:bind>
      </div>

      <div class="field">
        <spring:message code="snippetForm.contentPlaceholder" var="contentPlaceholder" />
        <spring:bind path="content">
          <form:textarea path="content" placeholder="${contentPlaceholder}" class="${status.error ? 'error' : ''}" />
          <form:errors path="content" class="errors" />
        </spring:bind>
      </div>

      <form:button type="submit"><spring:message code="snippetForm.save" /></form:button>
    </form:form>
  </jsp:body>
</t:main-template>

