package com.gfa.foxdining;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoxDiningApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void main_runs_without_exceptions() {

    org.junit.jupiter.api.Assertions.assertDoesNotThrow(
        () -> {
          FoxDiningApplication.main(new String[] {"--spring.main.web-environment=false"});
        });
  }
}
