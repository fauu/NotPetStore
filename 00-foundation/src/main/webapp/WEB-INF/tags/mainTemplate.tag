<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="pageTitle" fragment="true" %>
<%@ attribute name="extraStylesheetLinks" fragment="true" %>
<%@ attribute name="extraScriptLinks" fragment="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<title>
  <jsp:invoke fragment="pageTitle" /> · Not Pet Store (01 - Foundation)
</title>

<meta charset="UTF-8">

<link rel="stylesheet" href="//fonts.googleapis.com/css?family=Roboto:300,400,500">
<link rel="stylesheet" href="//fonts.googleapis.com/css?family=Inconsolata">
<link rel="stylesheet" href="//fonts.googleapis.com/icon?family=Material+Icons">
<jsp:invoke fragment="extraStylesheetLinks" />
<c:url var="mainStylesheetUrl" value="/public/css/main.css" />
<link rel="stylesheet" href="${mainStylesheetUrl}">

<header id="site-header">
  <c:url var="homeUrl" value="/" />
  <a id="site-id-group" href="${homeUrl}">
    <c:url var="logoUrl" value="/public/img/logo.png" />
    <img id="site-logo" src="${logoUrl}" alt="NPS logo">

    <h1 id="site-title">
      Not Pet Store <span id="site-subtitle">or Naive Pasting System</span>
    </h1>
  </a>

  <nav id="main-nav">
    <ul class="link-list">
      <li class="main-nav-link-container link-list-element">
        <a class="main-nav-link" href="${homeUrl}">
          <c:url var="iconNewUrl" value="/public/img/icon-new.png" />
          <img class="main-nav-link-icon" src="${iconNewUrl}" alt="New Snippet icon">

          <spring:message code="mainNav.addNewSnippet" />
        </a>
      <li class="main-nav-link-container link-list-element">
        <c:url var="browseUrl" value="/browse" />
        <a class="main-nav-link" href="${browseUrl}">
          <c:url var="iconBrowseUrl" value="/public/img/icon-browse.png" />
          <img class="main-nav-link-icon" src="${iconBrowseUrl}" alt="Browse Snippets icon">

          <spring:message code="mainNav.browseSnippets" />
        </a>
    </ul>
  </nav>
</header>

<jsp:include page="fragments/feedback/userActionFeedback.jsp" />

<main id="site-content">
  <jsp:doBody />
</main>

<footer id="site-footer">
  <span class="project-info">
    Not Pet Store or Naive Pasting System - A Sample Spring Web Application
  </span>
  <span class="license-info">
    Licensed under GPLv3
  </span>
</footer>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<jsp:invoke fragment="extraScriptLinks" />
<c:url var="jsUrlRoot" value="/public/js/" />
<script src="${jsUrlRoot}fixed-header.js"></script>
