package mocks;

import io.restassured.http.ContentType;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class MockServerUtils {

    public static void createMockWith(MockParameters parameters){
        HttpRequest request = getRequest(parameters);
        HttpResponse response = getResponse(parameters);

        new MockServerClient(parameters.getHost(), parameters.getPort())
                .when(request)
                .respond(response);
    }

    public static void resetAll(MockParameters parameters){
        new MockServerClient(parameters.getHost(), parameters.getPort()).
                reset();
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
