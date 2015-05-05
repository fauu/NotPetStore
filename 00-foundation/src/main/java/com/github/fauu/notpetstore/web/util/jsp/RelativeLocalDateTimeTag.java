package com.github.fauu.notpetstore.web.util.jsp;

import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;
import org.ocpsoft.prettytime.PrettyTime;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class RelativeLocalDateTimeTag extends SimpleTagSupport {

  private PrettyTime prettyTime;

  private LocalDateTime localDateTime;

  private String locale;

  public RelativeLocalDateTimeTag() {
    prettyTime = new PrettyTime();
  }

  @Override
  public void doTag() throws JspException, IOException {
    if (locale != null) {
      Locale locale = SetLocaleSupport.parseLocale(this.locale);
      prettyTime.setLocale(locale);
    }

    JspWriter out = getJspContext().getOut();

    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

    out.print(prettyTime.format(Date.from(instant)));
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

}