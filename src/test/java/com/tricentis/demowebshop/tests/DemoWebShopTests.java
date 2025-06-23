package com.tricentis.demowebshop.tests;

import com.tricentis.demowebshop.DemoWebShopBaseApiTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DemoWebShopTests extends DemoWebShopBaseApiTest {

    @Test
    void testAddItemToCart() {

        Response response = given()
                .contentType(ContentType.URLENC)
                .formParam("product_attribute_72_5_18", "53")
                .formParam("product_attribute_72_6_19", "54")
                .formParam("product_attribute_72_3_20", "57")
                .formParam("addtocart_72.EnteredQuantity", "1")
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        assertThat(response.jsonPath().getBoolean("success")).isEqualTo(true);
        assertThat(response.jsonPath().getString("message")).contains("The product has been added to your <a href=\"/cart\">shopping cart</a>");
        assertThat(response.jsonPath().getString("updatetopcartsectionhtml")).isEqualTo("(1)");

    }


    @Test
    void testAddItemToCartWithCookie() {

        Response response = given()
                .contentType(ContentType.URLENC)
                .cookie("Nop.customer", "8fdeabef-f8eb-4571-96cb-784f6ca3476d")
                .formParam("product_attribute_72_5_18", "53")
                .formParam("product_attribute_72_6_19", "54")
                .formParam("product_attribute_72_3_20", "57")
                .formParam("addtocart_72.EnteredQuantity", "1")
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        assertThat(response.jsonPath().getBoolean("success")).isEqualTo(true);
        assertThat(response.jsonPath().getString("message")).contains("The product has been added to your <a href=\"/cart\">shopping cart</a>");
        assertThat(Integer.parseInt(
                response.jsonPath().getString("updatetopcartsectionhtml").replaceAll("[()]", "")
                )
        ).isGreaterThan(1);

    }

}
