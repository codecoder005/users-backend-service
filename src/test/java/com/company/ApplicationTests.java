package com.company;

import com.company.config.TestSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"unit-test"})
@Import(TestSecurityConfiguration.class)
class
ApplicationTests {

	@Test
	void contextLoads() {
	}

}
