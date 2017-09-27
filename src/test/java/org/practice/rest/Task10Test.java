package org.practice.rest;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class Task10Test extends BaseTest {

    private String newServerIP = "1.2.3.4";


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
        String call = endpoint + CUSTOMER
                + SERVICE_RES + SERVICE + "deployments/" + newServerIP;
        String body = "instanceId=apiTest";
        int expectedStatus = 201;

        given().contentType(ContentType.TEXT)
                .body(body)
                .filters(new ResponseLoggingFilter(), new RequestLoggingFilter())
            .when().put(call)
            .then().statusCode(expectedStatus);

    }


    @Test(description = "Verify that the server is registered in the controller for the specific customer and\n" +
            "service instance", dependsOnMethods = "registerNewServer")
    public void checkNewServer(){
        int expectedStatus = 200;
        String call = endpoint + CUSTOMER + SERVICE_RES + SERVICE + "deployments/";

        String allServers = getBodyAsString(
                given().filters(new ResponseLoggingFilter(), new RequestLoggingFilter())
                        .when().get(call)
                        .then().statusCode(expectedStatus));

        Assert.assertTrue(allServers.contains(newServerIP),
                newServerIP + " should be shown in " + allServers);
    }


    @Test(description = "Wait for 5 minutes", dependsOnMethods = "checkNewServer", enabled = false)
    public void wait5Minutes(){
        try {
            TimeUnit.MINUTES.sleep(5);
        } catch (InterruptedException ex) {
            System.out.println("Failed to wait for 5 minutes");
            ex.printStackTrace();
        }
        System.out.println("Waited for 5 minutes");
    }


    @Test(description = "Verify that application on the newly registered server is accessible"
    , dependsOnMethods = "checkNewServer", alwaysRun = true)
    public void verifyApplication(){
        Assert.assertTrue(true, "Need to clarify");
    }


    @Test(description = "Clean up (remove the server from the Controller)", dependsOnMethods = "verifyApplication")
    public void cleanUpNewServer(){
        int expectedStatus = 204;
        String call = endpoint + CUSTOMER + SERVICE_RES + SERVICE + "deployments/" + newServerIP;

        given().filters(new ResponseLoggingFilter(), new RequestLoggingFilter())
                .when().delete(call)
                .then().statusCode(expectedStatus);
    }
}
