<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<c:if test="${not empty userActionFeedback}">
  <div class="user-feedback-box is-${userActionFeedback.type.code}">
    <s:message code="userActionFeedback.${userActionFeedback.code}" />
  </div>
</c:if>
