$(function() {

  var $header = $('#site-header'),
      $content = $('#site-content'),
      shrinkThreshold = 60,
      shrunkClassName = 'is-compact',
      shrunkAwareContentClassName = 'is-compact-header-aware',
      isShrunk = false;

  $(window).on('scroll', function() {
    var offset = window.pageYOffset || document.documentElement.scrollTop;

    if (!isShrunk && offset > shrinkThreshold)  {
      $header.addClass(shrunkClassName);
      $content.addClass(shrunkAwareContentClassName);
      isShrunk = true;
      console.log("shrunk");
    } else if (isShrunk && offset <= shrinkThreshold) {
      $header.removeClass(shrunkClassName);
      $content.removeClass(shrunkAwareContentClassName);
      isShrunk = false;
      console.log("unshrunk");
    }
  });

});