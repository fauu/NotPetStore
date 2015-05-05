<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="untitled" code="untitled" />
<c:set var="title" value="${not empty snippet.title ? snippet.title : untitled}" />

<t:main-template>
  <jsp:attribute name="pageName">
    <c:out value="${title}" />
  </jsp:attribute>

  <jsp:body>
    <h2><c:out value="${title}" /></h2>

  </jsp:body>
</t:main-template>

