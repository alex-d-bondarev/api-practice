import org.testng.annotations.BeforeTest;
import pojo.UserRequestPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import mocks.MockParameters;
import mocks.MockServerWrapper;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class PostJsonTestWithMocks {

    private static final ContentType JSON_TYPE = ContentType.JSON;
    private static final int HTTP_CREATE = 201;
    private static final int PORT = 1080;
    private static final String BASE_PATH = "/posts";
    private static final String MOCK_HOST = "localhost";
    private static final String URL_TEMPLATE = ConfigProperties.getProperty("url.template");

    private RequestSpecification requestSpecification;
    private UserRequestPojo testUser;

    @BeforeTest
    private void setUp() throws JsonProcessingException {
        setupMocks();
        setupRequestSpecification();
    }

    @Test
    public void contentTypeIsJson() {
        getValidatableResponseAfterPostRequest(testUser).contentType(JSON_TYPE);
    }

    @Test
    public void statusCodeIsCreated() {
        getValidatableResponseAfterPostRequest(testUser).statusCode(HTTP_CREATE);
    }

    @Test
    public void assertResponseBody() {
        UserRequestPojo result = getValidatableResponseAfterPostRequest(testUser).extract().as(UserRequestPojo.class);

        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getUserId(), result.getUserId());
        assertEquals(testUser.getTitle(), result.getTitle());
        assertEquals(testUser.getBody(), result.getBody());
    }

    private ValidatableResponse getValidatableResponseAfterPostRequest(Object body) {
        return given().spec(requestSpecification)
                .body(body)
                .when().post()
                .then();
    }

    private void setupMocks() throws JsonProcessingException {
        MockServerWrapper mock = new MockServerWrapper();
        testUser = buildTestUser();
        MockParameters parameters = createMockParametersFrom(testUser);
        mock.resetAll(parameters);
        mock.createMockWith(parameters);
    }

    private UserRequestPojo buildTestUser() {
        int recordId = 101;
        int userId = 2;
        String title = "test title";
        String body = "test responseBody";

        return UserRequestPojo.builder().
                withId(recordId).
                withUserId(userId).
                withTitle(title).
                withBody(body).
                build();
    }

    private MockParameters createMockParametersFrom(UserRequestPojo userRequestPojo) throws JsonProcessingException {
        String postMethod = "POST";
        String responseBody = "{\"id\": 101,\"userId\": 2,\"title\": \"test title\",\"body\": \"test responseBody\"}";
        String requestBody = new ObjectMapper().writeValueAsString(userRequestPojo);

        return MockParameters.getBuilder().
                withHost(MOCK_HOST).
                withPort(PORT).
                withMethod(postMethod).
                withPath(BASE_PATH).
                withContentType(JSON_TYPE).
                withStatusCode(HTTP_CREATE).
                withRequestBody(requestBody).
                withResponseBody(responseBody).
                build();
    }

    private void setupRequestSpecification() {
        String url = String.format(URL_TEMPLATE, MOCK_HOST, PORT, BASE_PATH);

        requestSpecification = new RequestSpecBuilder()
                .setContentType(JSON_TYPE)
                .setBaseUri(url)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
