<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>

<t:mainTemplate>
  <jsp:attribute name="siteTitle">
    <spring:message code="addNewSnippet" />
  </jsp:attribute>

  <jsp:body>
    <h2>
      <spring:message code="newSnippet" />
    </h2>

    <c:url var="actionPath" value="/" />
    <springform:form id="snippet-form" method="POST" action="${actionPath}" commandName="snippetForm">
      <div class="field-container">
        <spring:message var="titlePlaceholder" code="snippetForm.titlePlaceholder" />
        <spring:bind path="title">
          <springform:input id="snippet-title-field" class="${status.error ? 'field-invalid' : ''}" path="title" placeholder="${titlePlaceholder}" />
          <springform:errors class="field-errors" path="title" />
        </spring:bind>
      </div>

      <div class="field-container">
        <spring:message var="contentPlaceholder" code="snippetForm.contentPlaceholder" />
        <spring:bind path="content">
          <springform:textarea id="snippet-content-field" class="${status.error ? 'field-invalid' : ''}" path="content" placeholder="${contentPlaceholder}" />
          <springform:errors class="field-errors" path="content" />
        </spring:bind>
      </div>

      <div class="field-container">
        <label id="snippet-syntax-highlighting-field-label" class="field-label" for="snippet-syntax-highlighting-field"><spring:message code="snippet.syntaxHighlighting" /></label>
        <springform:select id="snippet-syntax-highlighting-field" path="syntaxHighlighting">
          <springform:options items="${snippetForm.syntaxHighlightingValues}" itemLabel="displayName" />
        </springform:select>
      </div>

      <div class="field-container">
        <label id="snippet-expires-field-label" class="field-label" for="snippet-expires-field"><spring:message code="snippetForm.expires" /></label>
        <springform:select id="snippet-expires-field" path="expirationMoment">
          <springform:options items="${snippetForm.expirationMomentValues}" itemLabel="displayName" />
        </springform:select>
      </div>

      <div class="field-container">
        <label id="snippet-owner-password-field-label" class="field-label" for="snippet-owner-password-field">
          <spring:message code="snippet.ownerPassword" />
          <span class="field-annotation"><spring:message code="form.optional" /></span>
        </label>
        <spring:bind path="ownerPassword">
          <springform:password id="snippet-owner-password-field" class="${status.error ? 'error' : ''}" path="ownerPassword" />
          <springform:errors class="field-errors" path="ownerPassword" />
        </spring:bind>
      </div>

      <div class="field-container">
        <label id="snippet-visibility-field-label" class="field-label" for="snippet-visibility-options"><spring:message code="snippet.visibility" /></label>
        <ul id="snippet-visibility-options">
          <c:forEach var="value" items="${snippetForm.visibilityValues}">
            <spring:message var="label" code="snippet.visibility.${value.toString().toLowerCase()}" />
            <li class="snippet-visibility-option-container">
              <springform:radiobutton class="snippet-visibility-option" path="visibility" value="${value}" label="${label}" />
          </c:forEach>
        </ul>
      </div>

      <springform:button id="snippet-submit-button" type="submit"><spring:message code="form.save" /></springform:button>
    </springform:form>
  </jsp:body>
</t:mainTemplate>

