<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="pageName" fragment="true" %>

<html>
<head>
  <title>
    <jsp:invoke fragment="pageName" /> · Not Pet Store (01 - Foundation)
  </title>
  <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Roboto">
  <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Inconsolata">
  <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />">
</head>
<body>
  <header>
    <a id="site-title-group" href="<c:url value="/" />">
      <img id="site-logo" src="<c:url value="/resources/img/logo.png" />" />
      <h1 id="site-title">Not Pet Store <span id="site-sub-title">or Naive Pasting System</span></h1>
    </a>
    <nav id="main-nav">
      <ul>
        <li>
          <a href="<c:url value="/" />">
            <img class="icon" src="<c:url value="/resources/img/icon-new.png" />" />
            Add new snippet
          </a>
        </li>
        <li>
          <a href="<c:url value="/browse" />">
            <img class="icon" src="<c:url value="/resources/img/icon-browse.png" />" />
            Browse snippets
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
