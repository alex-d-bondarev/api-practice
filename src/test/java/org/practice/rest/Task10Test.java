package org.practice.rest;

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
}
