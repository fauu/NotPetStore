<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${error != null}">
  <div class="error-message">
    <c:out value="${error}" />
  </div>
</c:if>

