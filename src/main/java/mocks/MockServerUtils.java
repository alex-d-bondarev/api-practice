package mocks;

import org.mockserver.client.MockServerClient;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class MockServerUtils {

    public static void createMockWith(MockParameters parameters){
        new MockServerClient(parameters.getHost(), parameters.getPort())
                .when(
                        request()
                                .withMethod(parameters.getMethod())
                                .withPath(parameters.getPath())
                )
                .respond(
                        response()
                                .withStatusCode(parameters.getStatusCode())
                                .withBody(parameters.getBody())
                );
    }

    public static void resetAll(MockParameters parameters){
        new MockServerClient(parameters.getHost(), parameters.getPort()).
                reset();
    }
}
