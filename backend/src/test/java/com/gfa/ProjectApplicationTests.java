package com.gfa;

import com.gfa.ProjectApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void main_runs_without_exceptions() {

    org.junit.jupiter.api.Assertions.assertDoesNotThrow(
        () -> {
          ProjectApplication.main(new String[] {"--spring.main.web-environment=false"});
        });
  }
}
