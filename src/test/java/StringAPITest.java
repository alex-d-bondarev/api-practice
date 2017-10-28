import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class StringAPITest extends StringRequester {
    private static final String CUSTOMER = "testgrid-QA-Assignment-Alexander-Bondarev-620f3c5e-3df7-4445-9f97-8f44a6bca12c/";
    private static final String SERVICE_RES = "services/";
    private static final String SERVICE = "fas:Fas_it69_QA_assignment_task1_and_2-20170927101840/";
    private static final String PROPERTIES_RES = "properties/";
    private static final String PROPERTY = "ms.reindexBatchSize";

    private String body;
    private String resource;
    private String response;

    private int expectedStatus;


    /**
     * Test status with GET and body not empty
     */
    @Test(description = "Verify that controller is accessible")
    public void verifyControllerAccessibility(){
        expectedStatus = 200;
        resource = "";

        response = getAsString(expectedStatus, resource);
        assertTextNotEmpty(response);
    }


    /**
     * Test each sub-resource with GET
     */
    @Test(description = "Verify that there is existing customer and service instance available")
    public void verifyExistingServices(){
        List<String> allCustomers;
        String newLineSymbol = "\\n";
        String call;
        String eachCustomerService;

        expectedStatus = 200;
        resource = "";

        response = getAsString(expectedStatus, resource);
        allCustomers = Arrays.asList(response.split(newLineSymbol));

        for(String customer : allCustomers){
            call = customer + "/" + SERVICE_RES;
            eachCustomerService = getAsString(expectedStatus, call);
            assertTextNotEmpty(eachCustomerService);
        }
    }


    /**
     * Test status with PUT
     */
    @Test(description = "Register new server for existing customer service instance")
    public void registerNewServer(){
        resource = CUSTOMER + SERVICE_RES + SERVICE + "deployments/1.2.3.4";
        expectedStatus = 201;
        body = "instanceId=apiTest";

        putAsString(expectedStatus, resource, body);
    }


    /**
     * Test status with DELETE
     */
    @Test(description = "Clean up (remove the server from the Controller)")
    public void cleanUpNewServer(){
        expectedStatus = 204;
        resource = CUSTOMER + SERVICE_RES + SERVICE + "deployments/1.2.3.4";

        deleteAsString(expectedStatus, resource);
    }


    /**
     * Combine post and get
     */
    @Test(description = "Test #11.2 POST with new value.")
    public void updateResource(){
        expectedStatus = 202;
        resource = CUSTOMER + SERVICE_RES + SERVICE + PROPERTIES_RES + PROPERTY;
        body = "25";

        postAsString(expectedStatus, resource, body);

        expectedStatus = 200;
        response = getAsString(expectedStatus, resource);
        Assert.assertEquals(body, response, "ms.reindexBatchSize should be updated after POST");
    }

    /////////////
    // Helpers //
    /////////////

    /**
     * @param text testNG assert that given text is not empty
     */
    private void assertTextNotEmpty(String text){
        Assert.assertNotEquals(text, "", "Response should not be empty");
    }
}
