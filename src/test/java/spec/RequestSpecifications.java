package spec;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;

public class RequestSpecifications {

    public static RequestSpecification requestSpecBase(){
        return with();
    }

    public static RequestSpecification requestSpecJsonBody(){
        return requestSpecBase()
                .contentType(ContentType.JSON);
    }

}
