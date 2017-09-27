package org.practice.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    protected static final String ROOT_URL = "http://34.251.228.27";
    protected static final String PORT = ":8081/";
    protected static final String CUSTOMERS_RES = "control/customers/";
    protected static final String CUSTOMER = "testgrid-QA-Assignment-Alexander-Bondarev-620f3c5e-3df7-4445-9f97-8f44a6bca12c/";
    protected static final String SERVICE_RES = "services/";
    protected static final String SERVICE = "fas:Fas_it69_QA_assignment_task1_and_2-20170927101840/";
    protected static final String PROPERTIES_RES = "properties/";
    protected static final String PROPERTY = "ms.reindexBatchSize";

    protected String endpoint;

    protected static RequestSpecification spec;

    @BeforeClass
    public void setUpSpec(){
        setUpEndPoint();

        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(endpoint)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    protected abstract void setUpEndPoint();

    protected String getBodyAsString(ValidatableResponse response){
        return response.contentType(ContentType.TEXT).extract().response().asString();
    }
}
