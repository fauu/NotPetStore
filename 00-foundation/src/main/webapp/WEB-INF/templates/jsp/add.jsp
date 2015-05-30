<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>



<t:mainTemplate>

  <jsp:attribute name="pageTitle">
    <spring:message code="addNewSnippet" />
  </jsp:attribute>

  <jsp:body>

    <h2><spring:message code="newSnippet" /></h2>

    <c:url var="actionPath" value="/" />
    <springform:form id="snippet-form" method="POST" action="${actionPath}" commandName="snippetForm">

      <ul class="field-list">

        <li class="field-list-element">

          <spring:bind path="title">

            <spring:message var="titlePlaceholder" code="snippetForm.titlePlaceholder" />
            <springform:input id="snippet-title-field" class="${status.error ? 'is-invalid' : ''}" path="title" placeholder="${titlePlaceholder}" />

            <springform:errors class="field-errors" path="title"/>

          </spring:bind>

        <li class="field-list-element">

          <spring:bind path="content">

            <spring:message var="contentPlaceholder" code="snippetForm.contentPlaceholder" />
            <springform:textarea id="snippet-content-field" class="${status.error ? 'is-invalid' : ''}" path="content" placeholder="${contentPlaceholder}" />

            <springform:errors class="field-errors" path="content" />

          </spring:bind>

        <li class="field-list-element">

          <label class="field-label" for="snippet-syntax-highlighting-field">
            <spring:message code="snippet.syntaxHighlighting" />
          </label>

          <springform:select id="snippet-syntax-highlighting-field" path="syntaxHighlighting">
            <springform:options items="${snippetForm.syntaxHighlightingValues}" itemLabel="displayName" />
          </springform:select>

        <li class="field-list-element">

          <label class="field-label" for="snippet-expires-field">
            <spring:message code="snippetForm.expires" />
          </label>

          <springform:select id="snippet-expires-field" path="expirationMoment">
            <springform:options items="${snippetForm.expirationMomentValues}" itemLabel="displayName" />
          </springform:select>

        <li class="field-list-element">

          <label class="field-label" for="snippet-owner-password-field">

            <spring:message code="snippet.ownerPassword" />

            <span class="field-annotation">
              <spring:message code="form.optional" />
            </span>

          </label>

          <spring:bind path="ownerPassword">

            <springform:password id="snippet-owner-password-field" class="${status.error ? 'is-invalid' : ''}" path="ownerPassword" />

            <springform:errors class="field-errors" path="ownerPassword" />

          </spring:bind>

        <li class="field-list-element">

          <label class="field-label" for="snippet-visibility-field">
            <spring:message code="snippet.visibility" />
          </label>

          <ul id="snippet-visibility-field" class="radio-select-field">

            <c:forEach var="value" items="${snippetForm.visibilityValues}">

              <li class="radio-select-option-container">

                <spring:message var="label" code="snippet.visibility.${value.toString().toLowerCase()}" />
                <springform:radiobutton path="visibility" value="${value}" label="${label}" />

            </c:forEach>

          </ul>

        <li class="field-list-element button-container">

          <springform:button type="submit">
            <spring:message code="form.save" />
          </springform:button>

      </ul>

    </springform:form>

  </jsp:body>

</t:mainTemplate>

