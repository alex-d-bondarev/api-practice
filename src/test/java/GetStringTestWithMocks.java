import io.restassured.response.ValidatableResponse;
import mocks.MockParameters;
import mocks.MockServerWrapper;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

public class GetStringTestWithMocks {
    private static final String URL_TEMPLATE = ConfigProperties.getProperty("url.template");
    private static final String BODY = "Alive";
    private static final String HOST = "localhost";
    private static final String PATH = "/status";
    private static final int HTTP_OK = 200;
    private static final int PORT = 1080;

    private MockParameters parameters;
    private MockServerWrapper mock;

    @BeforeTest
    public void setup() {
        mock = new MockServerWrapper();

        parameters = createParametersWithBody(BODY);
        mock.resetAll(parameters);
        createMockWithBody(BODY);
    }

    @Test
    public void mockedRequestHasStatusOK() {
        getValidatableResponseAfterGetRequest().statusCode(HTTP_OK);
    }

    @Test
    public void mockedRequestHasBodyAlive() {
        assertMockHasBody(BODY);
    }

    @Test
    public void mockIsOverridden() {
        String newBody = "It's Alive!";
        assertMockHasBody(BODY);

        mock.resetAll(parameters);
        createMockWithBody(newBody);
        assertMockHasBody(newBody);
    }

    private void assertMockHasBody(String body) {
        getValidatableResponseAfterGetRequest().body(is(body));
    }

    private String getRequestUrl() {
        return String.format(URL_TEMPLATE, HOST, PORT, PATH);
    }

    private ValidatableResponse getValidatableResponseAfterGetRequest() {
        return when().get(getRequestUrl()).then();
    }

    private void createMockWithBody(String body) {
        MockParameters parameters = createParametersWithBody(body);
        mock.createMockWith(parameters);
    }

    private MockParameters createParametersWithBody(String body) {
        String getMethod = "GET";

        return MockParameters.getBuilder().
                withHost(HOST).
                withPort(PORT).
                withMethod(getMethod).
                withPath(PATH).
                withStatusCode(HTTP_OK).
                withResponseBody(body).
                build();
    }
}
