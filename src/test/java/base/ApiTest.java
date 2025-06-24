package base;

import helpers.CustomAllureListener;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class ApiTest {

    @BeforeAll
    static void beforeAll() {
//        RestAssured.filters(new AllureRestAssured());
        RestAssured.filters(CustomAllureListener.withCustomTemplates());
    }
}
