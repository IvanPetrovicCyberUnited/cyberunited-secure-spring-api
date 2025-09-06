package com.cyberunited.secureapi;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthIntegrationTest {

    @LocalServerPort
    int port;

    @Test
    void health() {
        when().get("http://localhost:" + port + "/api/health").then().statusCode(200);
    }

    @Test
    void loginAndMe() {
        String token = given()
                .contentType("application/json")
                .body(Map.of("username", "user", "password", "password"))
                .post("http://localhost:" + port + "/api/auth/login")
                .then().statusCode(200)
                .extract().path("token");

        given().header("Authorization", "Bearer " + token)
                .get("http://localhost:" + port + "/api/users/me")
                .then().statusCode(200);
    }
}
