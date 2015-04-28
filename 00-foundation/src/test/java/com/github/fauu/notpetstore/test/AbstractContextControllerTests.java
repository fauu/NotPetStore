package com.github.fauu.notpetstore.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/spring/servlet-context.xml",
                       "file:src/test/resources/spring/test-context.xml"})
public abstract class AbstractContextControllerTests {

  @Autowired
  protected WebApplicationContext context;

}
