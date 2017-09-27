package org.practice.rest;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Task10Test extends BaseTest {

    protected void setUpEndPoint() {
        endpoint = ROOT_URL + PORT + CUSTOMERS_RES;
    }

    @Test(description = "Verify that controller is accessible")
    public void verifyControllerAccessibility(){
        String emprtyResult = "";
        int expectedStatus = 200;

        String response = getBodyAsString(
                given().spec(spec)
                .when().get()
                .then().statusCode(expectedStatus));

        Assert.assertNotEquals(response, emprtyResult, "Response should not be empty");
    }
}
