package logiclayer;

import datalayer.DataGateway;
import entity.Client;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Victor on 17.05.2016.
 */
public class ClientLogicTest {

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
    public void testAuthClientByUserId() {
        System.out.println("authClientByUserId");
        ClientLogic instance = new ClientLogic();

        int userid = 21;
        Client expResult = new Client("victor", "+79991234567", "vic", "password");
        expResult.setClientID(12);
        expResult.setID(21);
        Client result = instance.authClientByUserId(userid);
        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getClientID(), result.getClientID());
        assertEquals(expResult.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());

        userid = -1;
        expResult = null;
        result = instance.authClientByUserId(userid);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllClients() {
        System.out.println("getAllClients");
        ClientLogic instance = new ClientLogic();
        String result = instance.getAllClients();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetClientId() {
        System.out.println("getClientId");
        int userid = 21;
        ClientLogic instance = new ClientLogic();
        int expResult = 12;
        int result = instance.getClientId(userid);
        assertEquals(expResult, result);

        userid = 0;
        expResult = -1;
        result = instance.getClientId(userid);
        assertEquals(expResult, result);

        userid = -1;
        expResult = -1;
        result = instance.getClientId(userid);
        assertEquals(expResult, result);
    }

}