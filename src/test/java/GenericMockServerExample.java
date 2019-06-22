import org.mockserver.client.MockServerClient;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class GenericMockServerExample {
    private String url = "http://%s:%d%s";

    private String body = "Alive";
    private String host = "localhost";
    private String getMethod = "GET";
    private String path = "/status";

    private int expectedStatusCode = 200;
    private int port = 1080;

    @Test
    public void createExampleMock() {
        createMockWithBody(body);

        when().get(String.format(url, host, port, path)).
                then().statusCode(expectedStatusCode).body(is(body));
    }

    @Test
    public void mockIsOverriden(){
        String newBody = "It's Alive!";

        createMockWithBody(body);
        assertMockHasBody(body);

        resetAllMocks();
        createMockWithBody(newBody);
        assertMockHasBody(newBody);
    }

    private void assertMockHasBody(String body){
        when().get(String.format(url, host, port, path)).
                then().body(is(body));
    }

    private void createMockWithBody(String body){
        new MockServerClient(host, port)
                .when(
                        request()
                                .withMethod(getMethod)
                                .withPath(path)
                )
                .respond(
                        response()
                                .withStatusCode(expectedStatusCode)
                                .withBody(body)
                );
    }

    private void resetAllMocks(){
        new MockServerClient(host, port).reset();
    }
}
