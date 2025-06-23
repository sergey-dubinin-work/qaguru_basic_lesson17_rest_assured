package in.reqres.tests;

import base.ApiTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class ReqresInBaseApiTest extends ApiTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/";
    }
}
