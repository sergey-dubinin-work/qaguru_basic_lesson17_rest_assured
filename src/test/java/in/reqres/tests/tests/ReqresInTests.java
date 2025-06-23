package in.reqres.tests.tests;

import in.reqres.tests.ReqresInBaseApiTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresInTests extends ReqresInBaseApiTest {

    @Test
    void successfulLogin() {

        Response response = given()
                .when()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "email": "eve.holt@reqres.in",
                            "password": "cityslicka"
                        }
                        """)
                .post("/api/login")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        String token = response.path("token");
        String expectedToken = "QpwL5tke4Pnpja7X4";

        assertThat(token).isEqualTo(expectedToken);

    }


    @Test
    void unsuccessfulLogin() {

        Response response = given()
                .when()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "email": "eve.holt@reqres.in"
                        }
                        """)
                .post("/api/login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().response();

        String errorMessage = response.path("error");
        String expectedErrorMessage = "Missing password";

        assertThat(errorMessage).isEqualTo(expectedErrorMessage);

    }



}
