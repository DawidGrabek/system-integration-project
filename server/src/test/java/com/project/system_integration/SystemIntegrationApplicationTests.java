package com.project.system_integration;

import com.project.system_integration.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SystemIntegrationApplicationTests {

	@Autowired
	private UserController controller;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
