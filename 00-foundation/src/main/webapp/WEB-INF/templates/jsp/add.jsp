<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:main-template>
  <jsp:attribute name="pageName"><spring:message code="addNewSnippet" /></jsp:attribute>

  <jsp:body>
    <c:url value="/" var="actionPath" />
    <sf:form action="${actionPath}" commandName="snippetForm" method="POST">
      <div class="field">
        <spring:message code="snippetForm.titlePlaceholder" var="titlePlaceholder" />
        <spring:bind path="title">
          <sf:input path="title" placeholder="${titlePlaceholder}" class="${status.error ? 'error' : ''}"/>
          <sf:errors path="title" class="errors" />
        </spring:bind>
      </div>

      <div class="field">
        <spring:message code="snippetForm.contentPlaceholder" var="contentPlaceholder" />
        <spring:bind path="content">
          <sf:textarea path="content" placeholder="${contentPlaceholder}" class="${status.error ? 'error' : ''}" />
          <sf:errors path="content" class="errors" />
        </spring:bind>
      </div>

      <sf:button type="submit"><spring:message code="snippetForm.save" /></sf:button>
    </sf:form>
  </jsp:body>
</t:main-template>

