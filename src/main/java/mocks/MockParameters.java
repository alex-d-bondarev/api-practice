package mocks;

public class MockParameters {

    private String body;
    private String host;
    private String method;
    private String path;

    private int port;
    private int statusCode;

    public String getBody() {
        return body;
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

    public static final class MockParametersBuilder {
        private String body;
        private String host;
        private String method;
        private String path;
        private int port;
        private int statusCode;

        private MockParametersBuilder() {
        }

        public static MockParametersBuilder aMockParameters() {
            return new MockParametersBuilder();
        }

        public MockParametersBuilder withBody(String body) {
            this.body = body;
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
            mockParameters.body = this.body;
            mockParameters.statusCode = this.statusCode;
            mockParameters.method = this.method;
            mockParameters.path = this.path;
            mockParameters.host = this.host;
            mockParameters.port = this.port;
            return mockParameters;
        }
    }
}
