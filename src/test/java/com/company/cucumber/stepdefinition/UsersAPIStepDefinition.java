package com.company.cucumber.stepdefinition;

import com.company.config.TestSecurityConfiguration;
import com.company.cucumber.CucumberSpringConfiguration;
import com.company.model.response.PingAPIResponse;
import com.company.repository.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@Import(TestSecurityConfiguration.class)
@AutoConfigureMockMvc
public class UsersAPIStepDefinition extends CucumberSpringConfiguration {
    private static final String TEXT_PONG = "pong";

    @Autowired
    private UserRepository userRepository;

    private ResponseEntity<PingAPIResponse> response;

    @Before
    void setup() throws Exception {
        userRepository.deleteAll();
    }

    @Given("client want to check api v1 users is up and running")
    public void client_want_to_check_api_v1_users_is_up_and_running() {

    }

    @When("the client calls api v1 users ping endpoint")
    public void the_client_calls_api_v1_users_ping_endpoint() {
        response = testRestTemplate.getForEntity("/api/v1/users/ping", PingAPIResponse.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode().value());
    }
}
