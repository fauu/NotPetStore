<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="siteTitle" fragment="true" %>
<%@ attribute name="extraStylesheetDefs" fragment="true" %>

<title>
  <jsp:invoke fragment="siteTitle" /> · Not Pet Store (01 - Foundation)
</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Inconsolata">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<jsp:invoke fragment="extraStylesheetDefs" />
<c:url var="mainStylesheetUrl" value="/public/css/main.css" />
<link rel="stylesheet" href="${mainStylesheetUrl}">

<header id="site-header">
  <c:url var="homeUrl" value="/" />
  <a id="site-id-group" href="${homeUrl}">
    <c:url var="logoUrl" value="/public/img/logo.png" />
    <img id="site-logo" src="${logoUrl}" />
    <h1 id="site-title">Not Pet Store <span id="site-subtitle">or Naive Pasting System</span></h1>
  </a>
  <nav id="main-nav">
    <ul id="main-nav-links">
      <li class="main-nav-link-container">
        <a class="main-nav-link" href="${homeUrl}">
          <c:url var="iconNewUrl" value="/public/img/icon-new.png" />
          <img class="main-nav-link-icon" src="${iconNewUrl}" />
          <spring:message code="mainNav.addNewSnippet" />
        </a>
      <li class="main-nav-link-container">
        <c:url var="browseUrl" value="/browse" />
        <a class="main-nav-link" href="${browseUrl}">
          <c:url var="iconBrowseUrl" value="/public/img/icon-browse.png" />
          <img class="main-nav-link-icon" src="${iconBrowseUrl}" />
          <spring:message code="mainNav.browseSnippets" />
        </a>
    </ul>
  </nav>
</header>
<jsp:include page="../jsp/fragments/feedback/userActionFeedback.jsp" />
<main id="site-content">
  <jsp:doBody />
</main>
