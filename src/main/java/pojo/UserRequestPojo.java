package pojo;

public class UserRequestPojo {
    private int id;
    private int userId;
    private String title;
    private String body;

    public int getId(){
        return id;
    }

    public void setId(int newID){
        id = newID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static UserPostsBuilder builder(){
        return new UserPostsBuilder();
    }

    public static final class UserPostsBuilder {
        private int id;
        private int userId;
        private String title;
        private String body;

        private UserPostsBuilder() {
        }

        public UserPostsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public UserPostsBuilder withUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public UserPostsBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public UserPostsBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public UserRequestPojo build() {
            UserRequestPojo userRequestPojo = new UserRequestPojo();
            userRequestPojo.setId(id);
            userRequestPojo.setUserId(userId);
            userRequestPojo.setTitle(title);
            userRequestPojo.setBody(body);
            return userRequestPojo;
        }
    }
}
