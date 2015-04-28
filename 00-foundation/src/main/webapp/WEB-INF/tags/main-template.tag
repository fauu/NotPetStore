<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="pageName" fragment="true" %>

<html>
<head>
  <title>
    <jsp:invoke fragment="pageName" /> · Not Pet Store (01 - Foundation)
  </title>
  <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Roboto">
  <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Inconsolata">
  <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="<c:url value="/public/css/main.css" />">
</head>
<body>
  <header>
    <a id="site-title-group" href="<c:url value="/" />">
      <img id="site-logo" src="<c:url value="/public/img/logo.png" />" />
      <h1 id="site-title">Not Pet Store <span id="site-sub-title">or Naive Pasting System</span></h1>
    </a>
    <nav id="main-nav">
      <ul>
        <li>
          <a href="<c:url value="/" />">
            <img class="icon" src="<c:url value="/public/img/icon-new.png" />" />
            <spring:message code="mainNav.addNewSnippet" />
          </a>
        </li>
        <li>
          <a href="<c:url value="/browse" />">
            <img class="icon" src="<c:url value="/public/img/icon-browse.png" />" />
            <spring:message code="mainNav.browseSnippets" />
          </a>
        </li>
      </ul>
    </nav>
  </header>
  <main>
    <jsp:doBody />
  </main>
</body>
</html>
