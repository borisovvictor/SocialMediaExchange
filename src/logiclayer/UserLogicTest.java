package logiclayer;

import datalayer.DataGateway;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Victor on 21.05.2016.
 */
public class UserLogicTest {

    @BeforeClass
    public static void setUp() {
        DataGateway.initdb();
        DataGateway.connect();
    }

    @AfterClass
    public static void tearDown() {
        DataGateway.close();
    }

    @Test
    public void testAuthUser() throws Exception {
        System.out.println("authUser");
        String username = "";
        String password = "";
        UserLogic instance = new UserLogic();
        int expResult = -1;
        int result = instance.authUser(username, password);
        assertEquals(expResult, result);
    }

    @Test
    public void getUserIdByUsername() throws Exception {
        System.out.println("getUserIdByLogin");
        String username = "vic_performer";
        UserLogic instance = new UserLogic();
        int expResult = 31;
        int result = instance.getUserIdByUsername(username);
        assertEquals(expResult, result);
    }

}