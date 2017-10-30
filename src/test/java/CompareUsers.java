import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Compare user data in list of all users to specific user data
 *
 * Precondition:
 *  1. Get list of all users and put them to allPojoUsers list
 *
 * Test #1:
 *  1. Check if allPojoUsers has only 1 user with test id
 *
 * Test #2: (if Test #1 does not fail)
 *  1. Get user data from allPojoUsers by given id
 *  2. Get specific user data from system
 *  3. Check that data from step #1 and step #2 is equal.
 *
 *
 * Created on 30.10.2017.
 *
 * @author abondarev-sel
 */
public class CompareUsers extends JsonNestedRequester{
    private String resultTag = "result";
    private int expectedStatus = 200;
    private List<UserPojo> allPojoUsers;


    @DataProvider(name = "UserIDs")
    public Object[][] getUserIds(){
        int osadchiyID = 10;
        return new Object[][]{{osadchiyID}};
    }


    @BeforeClass(description = "Precondition")
    public void getAllUsers(){
        String allUsersResource = "all-users";
        allPojoUsers = new ArrayList<UserPojo>();

        List<HashMap> allUsers = getListOfNestedMapResponses(allUsersResource, expectedStatus, resultTag);

        for(HashMap user : allUsers){
            allPojoUsers.add(new PojoConverter<UserPojo>(UserPojo.class).convertFrom(user));
        }
    }


    @Test(description = "Test #1"
            , dataProvider = "UserIDs")
    public void checkIdInAllUsers(int userID){
        boolean thereIsSuchUser = false;
        int foundUsersWithSameID = 0;
        int expectedIdAmount = 1;

        String userNotFoundError = String.format("It is expected that user with id = %s is in the system", userID);
        String moreThan1UserFoundError = "There should be only 1 user with ID = " + userID;

        for (UserPojo user : allPojoUsers){
            if(user.getId() == userID){
                thereIsSuchUser = true;
                foundUsersWithSameID++;
            }
        }

        Assert.assertTrue(thereIsSuchUser, userNotFoundError);
        Assert.assertEquals(expectedIdAmount, foundUsersWithSameID, moreThan1UserFoundError);
    }


    @Test(description = "Compare all users data with specific user"
            , dependsOnMethods = "checkIdInAllUsers"
            , dataProvider = "UserIDs")
    public void compareUsers(int userID){
        UserPojo userFromList = null;
        UserPojo specificUser = getSpecificUser(userID);

        String userLastNameError = "Specific user and user from list should have same Last Names";

        for (UserPojo user : allPojoUsers){
            if(user.getId() == userID){
                userFromList = user;
                break;
            }
        }

        Assert.assertEquals(specificUser.getLastName(), userFromList.getLastName() , userLastNameError);
    }

    ////////////
    // Helper //
    ////////////

    private UserPojo getSpecificUser(int userID){
        String idKey = "id";
        String userResource = "user";

        HashMap specificUserMap = getNestedResponseAsMap
                (idKey, Integer.toString(userID), userResource, expectedStatus, resultTag);

        return new PojoConverter<UserPojo>(UserPojo.class).convertFrom(specificUserMap);
    }
}
