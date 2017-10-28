import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class JsonAPITest extends JsonRequester {

    @DataProvider(name = "demo")
    public Object[][] demoProvider(){
        PostsPojo post = new PostsPojo();
        post.setId(1);
        post.setUserId(2);
        post.setTitle("test title");
        post.setBody("test body");

        int status = 201;
        String resource = "posts";

        return new Object[][]{{status, resource, post}};
    }


    @Test(dataProvider = "demo")
    public void newPost(int status, String res, PostsPojo posts){
        PostsPojo result = (PostsPojo) postAsJson(status, res, posts);

        Assert.assertEquals(posts.getId(), result.getId(), "Request and response IDs should equal");
        Assert.assertEquals(posts.getUserId(), result.getUserId(), "Request and response userIds should equal");
        Assert.assertEquals(posts.getTitle(), result.getTitle(), "Request and response titles should equal");
        Assert.assertEquals(posts.getBody(), result.getBody(), "Request and response bodies should equal");
    }
}
