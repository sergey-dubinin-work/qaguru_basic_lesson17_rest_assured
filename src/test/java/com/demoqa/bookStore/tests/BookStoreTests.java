package com.demoqa.bookStore.tests;

import com.demoqa.bookStore.BookStoreBaseApiTest;
import com.demoqa.bookStore.models.account.RegisterUserResponse;
import com.demoqa.bookStore.models.account.TokenResponse;
import com.demoqa.bookStore.models.account.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
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

        User userToRegister = User.builder()
                .userName(faker.name().username())
                .password(faker.internet().password(19, 20, true, true, true))
                .build();

        RegisterUserResponse registeredUser = given()
                .spec(requestSpecJsonBody())
                .body(userToRegister)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec201Created())
                .extract().as(RegisterUserResponse.class);

        assertThat(registeredUser.getUsername()).isEqualTo(userToRegister.getUserName());

        given()
                .spec(requestSpecJsonBody())
                .body(userToRegister)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .spec(responseSpec200Ok())
                .body("", is(false));

        TokenResponse tokenBody =  given()
                .spec(requestSpecJsonBody())
                .body(userToRegister)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec200Ok())
                .body(matchesJsonSchemaInClasspath("jsonSchemas/generateTokenResponseSchema.json"))
                .extract().as(TokenResponse.class);

        assertAll(
                () -> assertThat(tokenBody.getStatus()).isEqualTo("Success"),
                () -> assertThat(tokenBody.getResult()).isEqualTo("User authorized successfully.")
        );

    }
}
