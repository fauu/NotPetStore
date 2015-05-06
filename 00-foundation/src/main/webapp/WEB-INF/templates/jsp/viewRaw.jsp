<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
  <head>
    <spring:message var="untitled" code="untitled" />
    <c:set var="title" value="${not empty snippet.title ? snippet.title : untitled}" />
    <title><c:out value="${title}" /></title>
    <meta charset="UTF-8" />
  </head>
  <body>
    <pre><c:out value="${snippet.content}" /></pre>
  </body>
</html>

