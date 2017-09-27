package org.practice.rest;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Task11Test extends BaseTest{

    private int reindexBatchSizeValue = 0;

    protected void setUpEndPoint() {
        endpoint = ROOT_URL + PORT + CUSTOMERS_RES + CUSTOMER
                + SERVICE_RES + SERVICE + PROPERTIES_RES + PROPERTY;
    }


    @Test(description = "Test #11.1 GET request test.")
    public void resourceOK(){
        int expectedStatus = 200;

        String response = getBodyAsString(
                given().spec(spec)
                .when().get()
                .then().statusCode(expectedStatus));

        reindexBatchSizeValue = Integer.parseInt(response);
    }


    @Test(description = "Test #11.2 POST with new value.", dependsOnMethods = "resourceOK")
    public void updateResource(){
        int expectedStatus = 202;
        int expectedSize = reindexBatchSizeValue+1;
        int newSize;

        given().spec(spec).body(expectedSize)
                .when().post()
                .then().statusCode(expectedStatus);

        String response = getBodyAsString(
                given().spec(spec)
                .when().get()
                .then());

        newSize = Integer.parseInt(response);

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
