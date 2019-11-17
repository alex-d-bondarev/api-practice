package mocks;

import io.restassured.http.ContentType;

public class MockParameters {
    private ContentType contentType;
    private int port;
    private int statusCode;
    private String responseBody;
    private String requestBody;
    private String host;
    private String method;
    private String path;

    public String getResponseBody() {
        return responseBody;
    }

    public String getHost() {
        return host;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public static MockParametersBuilder getBuilder(){
        return new MockParametersBuilder();
    }

    public static final class MockParametersBuilder {
        private String responseBody;
        private String requestBody;
        private String host;
        private String method;
        private String path;

        private int port;
        private int statusCode;

        private ContentType contentType;

        private MockParametersBuilder() {
        }

        public MockParametersBuilder withResponseBody(String body) {
            this.responseBody = body;
            return this;
        }

        public MockParametersBuilder withRequestBody(String body) {
            this.responseBody = body;
            return this;
        }

        public MockParametersBuilder withContentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public MockParametersBuilder withHost(String host) {
            this.host = host;
            return this;
        }

        public MockParametersBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

        public MockParametersBuilder withPath(String path) {
            this.path = path;
            return this;
        }

        public MockParametersBuilder withPort(int port) {
            this.port = port;
            return this;
        }

        public MockParametersBuilder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public MockParameters build() {
            MockParameters mockParameters = new MockParameters();
            mockParameters.responseBody = this.responseBody;
            mockParameters.requestBody = this.requestBody;
            mockParameters.statusCode = this.statusCode;
            mockParameters.method = this.method;
            mockParameters.path = this.path;
            mockParameters.host = this.host;
            mockParameters.port = this.port;
            mockParameters.contentType = this.contentType;
            return mockParameters;
        }
    }
}
