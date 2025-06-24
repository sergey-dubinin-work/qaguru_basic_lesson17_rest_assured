package com.demoqa.bookStore.tests;

import com.demoqa.bookStore.BookStoreBaseApiTest;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static spec.RequestSpecifications.requestSpecJsonBody;
import static spec.ResponseSpecifications.*;

public class BookStoreTests extends BookStoreBaseApiTest {

    Faker faker = new Faker();

    @Test
    void getBooksTest() {

        given()
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .body("books", hasSize(greaterThan(0)));

    }

    @Test
    void getBooksWithAllLogsTest() {

        given()
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .log().all()
                .body("books", hasSize(greaterThan(0)));

    }

    @Test
    void getBooksWithSomeLogsTest() {

        given()
                .when()
                .log().uri()
                .log().body()
                .get("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .body("books", hasSize(greaterThan(0)));

    }

    @Test
    void registerNewUser() {

        String username = faker.name().username();
        String password = faker.internet().password(19, 20, true, true, true);

        Response response = given()
                .spec(requestSpecJsonBody())
                .body(String.format("""
                        {
                          "userName": "%s",
                          "password": "%s"
                        }
                        """,
                        username,
                        password
                        ))
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec201Created())
                .body("username", equalTo(username))
                .extract().response();

        String userId = response.path("userID").toString();

        System.out.println(userId);

        given()
                .spec(requestSpecJsonBody())
                .body(String.format("""
                        {
                          "userName": "%s",
                          "password": "%s"
                        }
                        """,
                        username,
                        password
                ))
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .spec(responseSpec200Ok())
                .body("", is(false));

        given()
                .spec(requestSpecJsonBody())
                .body(String.format("""
                        {
                          "userName": "%s",
                          "password": "%s"
                        }
                        """,
                        username,
                        password
                ))
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec200Ok())
                .body(matchesJsonSchemaInClasspath("jsonSchemas/generateTokenResponseSchema.json"))
                .body("status", equalTo("Success"))
                .body("result", equalTo("User authorized successfully."));


    }
}
