<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="pageTitle" fragment="true" %>
<%@ attribute name="extraStylesheetDefs" fragment="true" %>

<html>
<head>
  <title>
    <jsp:invoke fragment="pageTitle" /> · Not Pet Store (01 - Foundation)
  </title>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Inconsolata">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  <jsp:invoke fragment="extraStylesheetDefs" />
  <c:url var="mainStylesheetUrl" value="/public/css/main.css" />
  <link rel="stylesheet" href="${mainStylesheetUrl}">
</head>
<body>
  <header>
    <c:url var="homeUrl" value="/" />
    <a id="site-title-group" href="${homeUrl}">
      <c:url var="logoUrl" value="/public/img/logo.png" />
      <img id="site-logo" src="${logoUrl}" />
      <h1 id="site-title">Not Pet Store <span id="site-sub-title">or Naive Pasting System</span></h1>
    </a>
    <nav role="main">
      <ul>
        <li>
          <a href="${homeUrl}">
            <c:url var="iconNewUrl" value="/public/img/icon-new.png" />
            <img class="icon" src="${iconNewUrl}" />
            <spring:message code="mainNav.addNewSnippet" />
          </a>
        </li>
        <li>
          <c:url var="browseUrl" value="/browse" />
          <a href="${browseUrl}">
            <c:url var="iconBrowseUrl" value="/public/img/icon-browse.png" />
            <img class="icon" src="${iconBrowseUrl}" />
            <spring:message code="mainNav.browseSnippets" />
          </a>
        </li>
      </ul>
    </nav>
  </header>
  <jsp:include page="../jsp/fragments/feedback/userActionFeedback.jsp" />
  <main>
    <jsp:doBody />
  </main>
</body>
</html>
