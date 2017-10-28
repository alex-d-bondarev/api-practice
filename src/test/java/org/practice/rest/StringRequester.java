package org.practice.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public abstract class StringRequester {

    private static final String BASE_URI = "http://34.251.228.27:8081/control/customers/";
    private RequestSpecification spec;


    /**
     * @param expectedStatus self-evident
     * @param resource       part of URL that will be appended to BASE_URI
     * @return               response as String
     */
    protected String getAsString(int expectedStatus, String resource){
        return getBodyAsString(
                given().spec(spec)
                        .when().get(resource)
                        .then().statusCode(expectedStatus));
    }


    /**
     * @param expectedStatus self-evident
     * @param resource       part of URL that will be appended to BASE_URI
     * @param body           request body
     * @return               response as String
     */
    protected String postAsString(int expectedStatus, String resource, String body){
        return getBodyAsString(
                given().spec(spec)
                        .body(body)
                        .when().post(resource)
                        .then().statusCode(expectedStatus));
    }


    /**
     * @param expectedStatus self-evident
     * @param resource       part of URL that will be appended to BASE_URI
     * @param body           request body
     * @return               response as String
     */
    protected String putAsString(int expectedStatus, String resource, String body){
        return getBodyAsString(
                given().spec(spec)
                        .body(body)
                        .when().put(resource)
                        .then().statusCode(expectedStatus));
    }


    /**
     * @param expectedStatus self-evident
     * @param resource       part of URL that will be appended to BASE_URI
     * @return               response as String
     */
    protected String deleteAsString(int expectedStatus, String resource){
        return getBodyAsString(
                given().spec(spec)
                        .when().delete(resource)
                        .then().statusCode(expectedStatus));
    }


    ///////////////////
    // Tests helpers //
    ///////////////////

    @BeforeClass
    public void setUpSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.TEXT)
                .setBaseUri(BASE_URI)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }


    /////////////
    // Helpers //
    /////////////

    /**
     * @param response Rest Assured response
     * @return         Response extracted into String
     */
    private String getBodyAsString(ValidatableResponse response){
        return response.contentType(ContentType.TEXT).extract().response().asString();
    }
}
