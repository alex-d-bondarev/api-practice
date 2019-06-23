import mocks.MockParameters;
import mocks.MockServerUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

public class GenericMockServerExample {
    private String url = "http://%s:%d%s";

    private String body = "Alive";
    private String host = "localhost";
    private String getMethod = "GET";
    private String path = "/status";

    private int okStatusCode = 200;
    private int port = 1080;

    private MockParameters parameters;

    @BeforeMethod
    public void setup() {
        parameters = createParametersWithBody(body);
        MockServerUtils.resetAll(parameters);
        createMockWithBody(body);
    }


    @Test
    public void mockedRequestHasStatusOK() {
        when().get(String.format(url, host, port, path)).
                then().statusCode(okStatusCode);
    }


    @Test
    public void mockedRequestHasBodyAlive() {
        assertMockHasBody(body);
    }

    @Test
    public void mockIsOverridden() {
        String newBody = "It's Alive!";

        assertMockHasBody(body);

        MockServerUtils.resetAll(parameters);
        createMockWithBody(newBody);
        assertMockHasBody(newBody);
    }


    private void assertMockHasBody(String body) {
        when().get(String.format(url, host, port, path)).
                then().body(is(body));
    }

    private void createMockWithBody(String body) {
        MockParameters parameters = createParametersWithBody(body);
        MockServerUtils.createMockWith(parameters);
    }

    private MockParameters createParametersWithBody(String body) {
        return MockParameters.MockParametersBuilder.aMockParameters().
                withHost(host).
                withPort(port).
                withMethod(getMethod).
                withPath(path).
                withStatusCode(okStatusCode).
                withBody(body).
                build();
    }
}
