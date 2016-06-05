package logiclayer;

import datalayer.DataGateway;
import entity.Request;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Victor on 04.06.2016.
 */
public class RequestLogicTest {

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
    public void testGetRequestById() throws Exception {
        System.out.println("getRequestById");
        RequestLogic instance = new RequestLogic();

        int requestID = 2;
        Request expResult = new Request(100, 7, "instagram", "sometext");
        expResult.setClientID(2);
        expResult.setID(requestID);
        expResult.setStatus(Request.Status.CREATED);
        Request result = instance.getRequestById(requestID);
        assertEquals(expResult.getID(), result.getID());
        assertEquals(expResult.getClientID(), result.getClientID());
        assertEquals(expResult.getStatus(), result.getStatus());
        assertEquals(expResult.getDescription(), result.getDescription());
        assertEquals(expResult.getSocialMedia(), result.getSocialMedia());

        requestID = -1;
        expResult = null;
        result = instance.getRequestById(requestID);
        assertEquals(expResult, result);

    }

    @Test
    public void testSetGetRequestStatusById() throws Exception {

        System.out.println("setGetRequestById");
        RequestLogic instance = new RequestLogic();

        int requestID = 2;
        Request.Status orig = instance.getRequestStatusById(requestID);
        Request.Status expResult = Request.Status.ASSIGNED;
        instance.setRequestStatusById(requestID, expResult);
        Request.Status result = instance.getRequestStatusById(requestID);
        assertEquals(expResult, result);
        instance.setRequestStatusById(requestID, orig);
    }

    @Test
    public void testGetAllRequests() throws Exception {
        System.out.println("getAllRequests");
        RequestLogic instance = new RequestLogic();
        String result = instance.getAllRequests();
        assertFalse(result.isEmpty());
    }


    @Test
    public void testAddRequest() {
        System.out.println("addRequest");
        RequestLogic instance = new RequestLogic();
        int result = instance.addRequest(2, 100, 7, "sometext", "instagram", Request.Status.CREATED);
        assertNotEquals(result, 0);
    }

}