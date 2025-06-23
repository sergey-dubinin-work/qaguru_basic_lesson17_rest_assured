package com.tricentis.demowebshop;

import base.ApiTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class DemoWebShopBaseApiTest extends ApiTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com/";
    }
}
