package com.cyberunited.secapi;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerTest {
    @LocalServerPort
    int port;

    @Test
    void healthOk() {
        RestAssured.when().get("http://localhost:" + port + "/api/health")
                .then().statusCode(200).body(equalTo("OK"));
    }
}
