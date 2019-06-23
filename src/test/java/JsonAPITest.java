import beans.UserPosts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import mocks.MockParameters;
import mocks.MockServerUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class JsonAPITest {

    private final static String BASE_URI = ConfigProperties.getProperty("api.base_uri");

    private String host = "localhost";
    private String path = "/posts";

    private int createStatusCode = 201;
    private int port = 1080;

    private ContentType contentType = ContentType.JSON;
    private RequestSpecification requestSpecification;
    private UserPosts requestBody;

    @BeforeMethod
    private void setUp() throws JsonProcessingException {
        setupMocks();
        setupRequestSpecification();
    }

    @Test
    public void contentTypeIsJson(){
        requestThenWith(requestBody).contentType(contentType);
    }

    @Test
    public void statusCodeIsCreated(){
        requestThenWith(requestBody).statusCode(createStatusCode);
    }

    @Test
    public void assertResponseBody(){
        UserPosts result = requestThenWith(requestBody).extract().as(UserPosts.class);

        Assert.assertEquals(result.getId(), requestBody.getId());
        Assert.assertEquals(result.getUserId(), requestBody.getUserId());
        Assert.assertEquals(result.getTitle(), requestBody.getTitle());
        Assert.assertEquals(result.getBody(), requestBody.getBody());
    }


    private ValidatableResponse requestThenWith(Object body){
        return given().spec(requestSpecification)
                .body(body)
                .when().post()
                .then();
    }

    private void setupMocks() throws JsonProcessingException {
        requestBody = new UserPosts();
        requestBody.setId(101);
        requestBody.setUserId(2);
        requestBody.setTitle("test title");
        requestBody.setBody("test responseBody");

        MockParameters parameters = createMockParameters(requestBody);

        MockServerUtils.resetAll(parameters);
        MockServerUtils.createMockWith(parameters);
    }

    private MockParameters createMockParameters(UserPosts userPosts) throws JsonProcessingException {
        String responseBody = "{\"id\": 101,\"userId\": 2,\"title\": \"test title\",\"body\": \"test responseBody\"}";
        String postMethod = "POST";

        String requestBody = new ObjectMapper().writeValueAsString(userPosts);

        return MockParameters.MockParametersBuilder.aMockParameters().
                withHost(host).
                withPort(port).
                withMethod(postMethod).
                withPath(path).
                withContentType(contentType).
                withStatusCode(createStatusCode).
                withRequestBody(requestBody).
                withResponseBody(responseBody).
                build();
    }

    private void setupRequestSpecification(){
        String url = String.format(BASE_URI, host, port, path);

        requestSpecification = new RequestSpecBuilder()
                .setContentType(contentType)
                .setBaseUri(url)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
