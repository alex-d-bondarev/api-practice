import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class StringAPITest {
    private RequestSpecification spec;

    private static final String CUSTOMER = "testgrid-QA-Assignment-Alexander-Bondarev-620f3c5e-3df7-4445-9f97-8f44a6bca12c/";
    private static final String DEPLOYMENTS = "deployments/1.2.3.4";
    private static final String SERVICE_RES = "services/";
    private static final String SERVICE = "fas:Fas_it69_QA_assignment_task1_and_2-20170927101840/";

    private String baseURI = ConfigProperties.getProperty("string.base_uri");

    @BeforeTest
    public void setUpSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.TEXT)
                .setBaseUri(baseURI)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    /**
     * Test status with GET and body not empty
     */
    @Test(description = "Verify that controller is accessible")
    public void verifyControllerAccessibility(){
        int expectedStatus = 200;
        String resource = "";

        assertResponseHasNonEmptyTextAndStatusIs(
                spec.when().get(resource).then(),
                expectedStatus);
    }


    /**
     * Test each sub-resource with GET
     */
    @Test(description = "Verify that there is existing x and service instance available")
    public void verifyExistingServices(){
        List<String> allCustomers;
        String newLineSymbol = "\\n";
        String call;
        String resource = "";
        ValidatableResponse then;
        int expectedStatus = 200;

        allCustomers = Arrays.asList(
                        extractStringFromResponse(spec.when().get(resource)
                                                        .then().statusCode(expectedStatus)).split(newLineSymbol));

        for(String customer : allCustomers){
            call = customer + "/" + SERVICE_RES;
            then = spec.when().get(call).then();
            assertResponseHasNonEmptyTextAndStatusIs(then, expectedStatus);
        }
    }


    /**
     * Test status with PUT
     */
    @Test(description = "Register new server for existing customer service instance")
    public void registerNewServer(){
        String resource = CUSTOMER + SERVICE_RES + SERVICE + DEPLOYMENTS;
        int expectedStatus = 201;
        String body = "instanceId=apiTest";

        spec.body(body).when().put(resource)
                        .then().statusCode(expectedStatus);
    }


    /**
     * Test status with DELETE
     */
    @Test(description = "Clean up (remove the server from the Controller)")
    public void cleanUpNewServer(){
        int expectedStatus = 204;
        String resource = CUSTOMER + SERVICE_RES + SERVICE + DEPLOYMENTS;

        spec.when().delete(resource)
                .then().statusCode(expectedStatus);
    }


    /**
     * Combine post and get
     */
    @Test(description = "Test #11.2 POST with new value.")
    public void updateResource(){
        int expectedPostStatus = 202;
        int expectedGetStatus = 200;
        String body = "25";
        String properties = "properties/ms.reindexBatchSize";
        String resource = CUSTOMER + SERVICE_RES + SERVICE + properties;

        spec.body(body).when().post(resource)
                        .then().statusCode(expectedPostStatus);

        String response = extractStringFromResponse(
                spec.when().get(resource).then().statusCode(expectedGetStatus));

        Assert.assertEquals(body, response, "ms.reindexBatchSize should be updated after POST");
    }



    private void assertResponseHasNonEmptyTextAndStatusIs(ValidatableResponse then, int expectedStatus){
        String response = extractStringFromResponse(then.statusCode(expectedStatus));
        Assert.assertTrue(StringUtils.isNotEmpty(response), "Response should not be empty");
    }

    private String extractStringFromResponse(ValidatableResponse then){
        return then.contentType(ContentType.TEXT).extract().response().asString();
    }
}
