package com.demoqa.bookStore;

import base.ApiTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BookStoreBaseApiTest extends ApiTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://demoqa.com";
    }

}
