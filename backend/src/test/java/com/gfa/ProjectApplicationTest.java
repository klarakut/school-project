package com.gfa;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
class ProjectApplicationTest {

  @Test
  void contextLoads() {}

  @Test
  void main_runs_without_exceptions() {

    assertDoesNotThrow(
        () -> {
          ProjectApplication.main(new String[] {"--spring.main.web-environment=false"});
        });
  }
}
