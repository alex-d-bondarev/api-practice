package mocks;

import io.restassured.http.ContentType;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class MockServerWrapper {

    private static MockServerClient mockClient;

    public void createMockWith(MockParameters parameters){
        HttpRequest request = getRequest(parameters);
        HttpResponse response = getResponse(parameters);

        getMockClient(parameters.getHost(), parameters.getPort())
                .when(request)
                .respond(response);
    }

    public void resetAll(MockParameters parameters){
        getMockClient(parameters.getHost(), parameters.getPort()).
                reset();
    }

    private MockServerClient getMockClient(String host, int port){
        if(mockClient == null)
            mockClient = new MockServerClient(host, port);

        return mockClient;
    }


    private static HttpRequest getRequest(MockParameters parameters){
        return request()
                .withMethod(parameters.getMethod())
                .withPath(parameters.getPath());
    }

    private static HttpResponse getResponse(MockParameters parameters){
        HttpResponse response = response()
                .withStatusCode(parameters.getStatusCode())
                .withBody(parameters.getResponseBody());

        if(parameters.getContentType() != null && parameters.getContentType().equals(ContentType.JSON)) {
            response.withHeader(new Header("Content-Type", "application/json; charset=utf-8"));
        }

        return response;
    }
}
