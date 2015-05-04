package com.github.fauu.notpetstore.it;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/spring/application-context.xml",
                       "file:src/main/resources/spring/servlet-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractIntegrationTests {

  @Autowired
  protected WebApplicationContext applicationContext;

  protected MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = webAppContextSetup(applicationContext).build();
  }

}
