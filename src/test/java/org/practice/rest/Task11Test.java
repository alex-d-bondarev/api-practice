package org.practice.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Task11Test{

    private static final String ROOT_URL = "http://34.251.228.27";
    private static final String PORT = ":8081/";
    private static final String CUSTOMERS_RES = "control/customers/";
    private static final String CUSTOMER = "testgrid-QA-Assignment-Alexander-Bondarev-620f3c5e-3df7-4445-9f97-8f44a6bca12c/";
    private static final String SERVICE_RES = "services/";
    private static final String SERVICE = "fas:Fas_it69_QA_assignment_task1_and_2-20170927101840/";
    private static final String PROPERTIES_RES = "properties/";
    private static final String PROPERTY = "ms.reindexBatchSize";

    private int reindexBatchSizeValue = 0;

    private static RequestSpecification spec;


    @BeforeClass
    public void setUpSpec(){
        String endpoint = ROOT_URL + PORT + CUSTOMERS_RES + CUSTOMER
                + SERVICE_RES + SERVICE + PROPERTIES_RES + PROPERTY;

        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(endpoint)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }


    @Test(description = "Test #11.1 GET request test.")
    public void resourceOK(){
        int expectedStatus = 200;

        Response response = given().spec(spec)
                .when().get()
                .then().statusCode(expectedStatus)
                .contentType(ContentType.TEXT).extract().response();

        reindexBatchSizeValue = Integer.parseInt(response.asString());
    }


    @Test(description = "Test #11.2 POST with new value.", dependsOnMethods = "resourceOK")
    public void updateResource(){
        int expectedStatus = 202;
        int expectedSize = reindexBatchSizeValue++;
        int newSize;

        given().spec(spec).body(expectedSize)
                .when().post()
                .then().statusCode(expectedStatus);

        Response response  = given().spec(spec)
                .when().get()
                .then().contentType(ContentType.TEXT).extract().response();

        newSize = Integer.parseInt(response.asString());

        Assert.assertEquals(expectedSize, newSize, "ms.reindexBatchSize should be updated after POST");

    }


    @Test(description = "Test #11.3 POST no values test.")
    public void postNoValues() {
        int expectedStatus = 406;
        String noValues = "";

        given().spec(spec).body(noValues)
                .when().post()
                .then().statusCode(expectedStatus);
    }


    @Test(description = "Test #11.4 POST incorrect body.")
    public void postIncorrectBody() {
        int expectedStatus = 304;
        String noValues = "{\"value\": 7}";

        given().spec(spec).body(noValues)
                .when().post()
                .then().statusCode(expectedStatus);
    }
}
