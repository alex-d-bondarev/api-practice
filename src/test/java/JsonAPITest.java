import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class JsonAPITest extends JsonRequester {

    @DataProvider(name = "demo")
    public Object[][] demoProvider(){
        PostsPojo requestBody = new PostsPojo();
        requestBody.setId(101);
        requestBody.setUserId(2);
        requestBody.setTitle("test title");
        requestBody.setBody("test body");

        int status = 201;
        String resource = "posts";

        return new Object[][]{{status, resource, requestBody}};
    }


    @Test(dataProvider = "demo")
    public void newPost(int status, String res, PostsPojo posts){
        PostsPojo result = (PostsPojo) postAsJson(status, res, posts);

        Assert.assertEquals(result.getId(), posts.getId(), "Request and response IDs should equal");
        Assert.assertEquals(result.getUserId(), posts.getUserId(), "Request and response userIds should equal");
        Assert.assertEquals(result.getTitle(), posts.getTitle(), "Request and response titles should equal");
        Assert.assertEquals(result.getBody(), posts.getBody(), "Request and response bodies should equal");
    }
}
