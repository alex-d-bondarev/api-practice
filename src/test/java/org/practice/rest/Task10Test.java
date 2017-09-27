package org.practice.rest;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Task10Test extends BaseTest {

    protected void setUpEndPoint() {
        endpoint = ROOT_URL + PORT + CUSTOMERS_RES;
    }


    @Test(description = "Verify that controller is accessible")
    public void verifyControllerAccessibility(){
        int expectedStatus = 200;

        String response = getBodyAsString(
                given().spec(spec)
                .when().get()
                .then().statusCode(expectedStatus));

        assertBodyNotEmpty(response);
    }

    @Test(description = "Verify that there is existing customer and service instance available")
    public void verifyExistingServices(){
        List<String> allCustomers = null;
        String newLineSymbol = "\\n";
        int expectedStatus = 200;

        String response = getBodyAsString(
                given().spec(spec)
                        .when().get()
                        .then());

        allCustomers = Arrays.asList(response.split(newLineSymbol));

        for(String customer : allCustomers){
            String call = customer + "/" + SERVICE_RES;

            String eachCustomerService = getBodyAsString(
                    given().spec(spec)
                    .when().get(call)
                    .then().statusCode(expectedStatus));
            assertBodyNotEmpty(eachCustomerService);
        }
    }

    @Test(description = "Register new server for existing customer service instance")
    public void registerNewServer(){
        String newServerIP = "1.2.3.4";
        String call = ROOT_URL + PORT + CUSTOMERS_RES + CUSTOMER
                + SERVICE_RES + SERVICE + "deployments/" + newServerIP;
        String body = "instanceId=apiTest";
        int expectedStatus = 201;

        given().contentType(ContentType.TEXT)
                .body(body)
                .filters(new ResponseLoggingFilter(), new RequestLoggingFilter())
            .when().put(call)
            .then().statusCode(expectedStatus);
    }
}
