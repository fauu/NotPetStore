$(function() {

  var $header = $('#site-header'),
      $content = $('#site-content'),
      shrinkThreshold = 10,
      shrunkClassName = 'is-compact',
      shrunkAwareContentClassName = 'is-compact-header-aware',
      isShrunk = false;

  $(window).on('scroll', function() {
    var offset = window.pageYOffset || document.documentElement.scrollTop;

    if (!isShrunk && offset > shrinkThreshold)  {
      $header.addClass(shrunkClassName);
      $content.addClass(shrunkAwareContentClassName);
      isShrunk = true;
    } else if (isShrunk && offset <= shrinkThreshold) {
      $header.removeClass(shrunkClassName);
      $content.removeClass(shrunkAwareContentClassName);
      isShrunk = false;
    }
  });

});