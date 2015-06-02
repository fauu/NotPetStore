<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<t:mainTemplate>
  <jsp:attribute name="pageTitle">
    <s:message code="addNewSnippet" />
  </jsp:attribute>

  <jsp:body>
    <h2><s:message code="newSnippet" /></h2>

    <c:url var="actionPath" value="/" />
    <sf:form id="snippet-form" method="POST" action="${actionPath}" commandName="snippetForm">
      <ul class="field-list">
        <li class="field-list-element">
          <s:bind path="title">
            <s:message var="titlePlaceholder" code="snippetForm.titlePlaceholder" />
            <sf:input id="snippet-title-field" class="${status.error ? 'is-invalid' : ''}" path="title" placeholder="${titlePlaceholder}" />

            <sf:errors class="field-errors" path="title"/>
          </s:bind>
        <li class="field-list-element">
          <s:bind path="content">
            <s:message var="contentPlaceholder" code="snippetForm.contentPlaceholder" />
            <sf:textarea id="snippet-content-field" class="${status.error ? 'is-invalid' : ''}" path="content" placeholder="${contentPlaceholder}" />

            <sf:errors class="field-errors" path="content" />
          </s:bind>
        <li class="field-list-element">
          <label class="field-label" for="snippet-syntax-highlighting-field">
            <s:message code="snippet.syntaxHighlighting" />
          </label>

          <sf:select id="snippet-syntax-highlighting-field" path="syntaxHighlighting">
            <sf:options items="${snippetForm.syntaxHighlightingValues}" itemLabel="displayName" />
          </sf:select>
        <li class="field-list-element">
          <label class="field-label" for="snippet-expires-field">
            <s:message code="snippetForm.expires" />
          </label>

          <sf:select id="snippet-expires-field" path="expirationMoment">
            <sf:options items="${snippetForm.expirationMomentValues}" itemLabel="displayName" />
          </sf:select>
        <li class="field-list-element">
          <label class="field-label" for="snippet-owner-password-field">
            <s:message code="snippet.ownerPassword" />
            <span class="field-annotation">
              <s:message code="form.optional" />
            </span>
          </label>

          <s:bind path="ownerPassword">
            <sf:password id="snippet-owner-password-field" class="${status.error ? 'is-invalid' : ''}" path="ownerPassword" />

            <sf:errors class="field-errors" path="ownerPassword" />
          </s:bind>
        <li class="field-list-element">
          <label class="field-label" for="snippet-visibility-field">
            <s:message code="snippet.visibility" />
          </label>

          <ul id="snippet-visibility-field" class="radio-select-field">
            <c:forEach var="value" items="${snippetForm.visibilityValues}">
              <li class="radio-select-option-container">
                <s:message var="label" code="snippet.visibility.${value.toString().toLowerCase()}" />
                <sf:radiobutton path="visibility" value="${value}" label="${label}" />
            </c:forEach>
          </ul>
        <li class="field-list-element button-container">
          <sf:button type="submit">
            <s:message code="form.save" />
          </sf:button>
      </ul>
    </sf:form>
  </jsp:body>
</t:mainTemplate>

