import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonNestedRequester {


    protected RequestSpecification spec = new RequestSpecBuilder()
            .addHeader("token", ConfigProperties.getProperty("proveng_access_token"))
            .setContentType(ContentType.JSON)
            .setBaseUri("http://proveng.cloud.provectus-it.com/rest/v1/")
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();


    protected HashMap getNestedResponseAsMap(String paramKey, String paramValue, String resource, int status, String nestedPath){
        return given().spec(spec)
                        .queryParam(paramValue, paramKey)
                .when().get(resource)
                .then().statusCode(status)
                        .extract().body().jsonPath().get(nestedPath);
    }

    protected List<HashMap> getListOfNestedMapResponses(String resource, int status, String nestedPath){
        return given().spec(spec)
                .when().get(resource)
                .then().statusCode(status)
                        .extract().body().jsonPath().getList(nestedPath);
    }

}
