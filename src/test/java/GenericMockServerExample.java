import mocks.MockParameters;
import mocks.MockServerUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

public class GenericMockServerExample {
    private String url = "http://%s:%d%s";

    private String body = "Alive";
    private String host = "localhost";
    private String getMethod = "GET";
    private String path = "/status";

    private int expectedStatusCode = 200;
    private int port = 1080;

    private MockParameters parameters;

    @BeforeClass
    public void setup(){
        parameters = MockParameters.MockParametersBuilder.aMockParameters().
                withHost(host).
                withPort(port).
                withMethod(getMethod).
                withPath(path).
                withStatusCode(expectedStatusCode).
                withBody(body).
                build();

        MockServerUtils.resetAll(parameters);
    }


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

        MockServerUtils.resetAll(parameters);
        createMockWithBody(newBody);
        assertMockHasBody(newBody);
    }


    private void assertMockHasBody(String body){
        when().get(String.format(url, host, port, path)).
                then().body(is(body));
    }

    private void createMockWithBody(String body){
        MockParameters parameters = MockParameters.MockParametersBuilder.aMockParameters().
                withHost(host).
                withPort(port).
                withMethod(getMethod).
                withPath(path).
                withStatusCode(expectedStatusCode).
                withBody(body).
                build();

        MockServerUtils.createMockWithParameters(parameters);
    }
}
