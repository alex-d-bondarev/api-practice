import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class JsonRequester {

    private String BASE_URI = ConfigProperties.getProperty("api.base_uri");
    private RequestSpecification spec;

    protected Object postAsJson(int expectedStatus, String resource, Object body){
        return given().spec(spec)
                    .body(body)
                .when().post(resource)
                .then().statusCode(expectedStatus)
                    .extract().as(body.getClass());
    }

    ///////////////////
    // Tests helpers //
    ///////////////////

    @BeforeClass
    public void setUpSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
