package com.demoqa.bookStore.tests;

import com.demoqa.bookStore.BookStoreBaseApiTest;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
                .contentType(ContentType.JSON)
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
                .statusCode(HttpStatus.SC_CREATED)
                .body("username", equalTo(username))
                .extract().response();

        String userId = response.path("userID").toString();

        System.out.println(userId);

        given()
                .contentType(ContentType.JSON)
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
                .statusCode(HttpStatus.SC_OK)
                .body("", is(false));

        given()
                .contentType(ContentType.JSON)
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
                .statusCode(HttpStatus.SC_OK)
                .body("status", equalTo("Success"))
                .body("result", equalTo("User authorized successfully."));


    }
}
