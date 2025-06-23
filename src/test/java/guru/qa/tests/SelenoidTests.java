package guru.qa.tests;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {

    @Test
    void checkTotalSelenoidBrowsers() {

        given()
                .when()
                .get("http://localhost:4444/status")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", is(5));

    }

    @Test
    void checkTotalSelenoidBrowsersWithoutGet() {

        get("http://localhost:4444/status")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", is(5));

    }

    @Test
    void checkTotalSelenoidBrowsersCheckChromeVersion() {

        get("http://localhost:4444/status")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("browsers.chrome", hasKey("128.0"));

    }

    @Test
    void checkTotalSelenoidBrowsersDebugBadPractice() {

        Response response = get("http://localhost:4444/status")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        String responseBody = response.asString();

        String expectedResponseBody = """
                {"total":5,"used":0,"queued":0,"pending":0,"browsers":{"chrome":{"127.0":{},"128.0":{}},"firefox":{"124.0":{},"125.0":{}},"opera":{"108.0":{},"109.0":{}},"safari":{"15.0":{}}}}
                """;

        assertEquals(expectedResponseBody, responseBody);
    }

    @Test
    void checkTotalSelenoidBrowsersDebugGoodPractice() {

        Response response = get("http://localhost:4444/status")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer responseBody = response.path("total");

        assertEquals(5, responseBody);
    }

    @Test
    void responseExamples() {
        Response response =
                get("http://localhost:4444/status")
                .then()
                .extract().response();

        System.out.println("Response: " + response);
        System.out.println("Response.toString(): " + response.toString());
        System.out.println("Response.asString(): " + response.asString());
        System.out.println("Response total: " + response.path("total"));
        System.out.println("Response browsers.chrome: " + response.path("browsers.chrome"));
    }

    @Test
    void checkTotalSelenoidBrowsersWithAssertJ() {

        Response response = get("http://localhost:4444/status")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer responseBody = response.path("total");

        assertThat(responseBody).isEqualTo(5);
    }

}
