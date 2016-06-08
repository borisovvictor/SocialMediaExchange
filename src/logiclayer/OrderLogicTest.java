package logiclayer;

import datalayer.DataGateway;
import entity.Order;
import entity.Request;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;


public class OrderLogicTest {
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
    public void testGetOrderById() throws Exception {
        System.out.println("getOrderById");
        ExchangeService instance = new ExchangeService();

        int orderID = 16;
        Order expResult = new Order(12, 15, "cond_text");
        expResult.setID(orderID);
        expResult.setOrderStatus(Order.Status.CREATED);
        Order result = instance.getOrderById(orderID);
        assertEquals(expResult.getID(), result.getID());
        assertEquals(expResult.getClientID(), result.getClientID());
        assertEquals(expResult.getOfferID(), result.getOfferID());
        assertEquals(expResult.getOrderStatus(), result.getOrderStatus());
        assertEquals(expResult.getSpecialConditions(), result.getSpecialConditions());

        orderID = -1;
        expResult = null;
        result = instance.getOrderById(orderID);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetGetOrderStatusById() throws Exception {
        System.out.println("setGetOrderById");
        ExchangeService instance = new ExchangeService();

        int orderID = 16;
        Order.Status orig = instance.getOrderStatusById(orderID);
        Order.Status expResult = Order.Status.APPROVED;
        instance.setOrderStatusById(orderID, expResult);
        Order.Status result = instance.getOrderStatusById(orderID);
        assertEquals(expResult, result);
        instance.setOrderStatusById(orderID, orig);
    }

    @Test
    public void testGetAllOrders() throws Exception {
        System.out.println("getAllOrders");
        ExchangeService instance = new ExchangeService();
        String result = instance.getAllOrders();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testAddOrder() {
        System.out.println("addOrder");

        ExchangeService instance = new ExchangeService();
        int result = instance.addOrder(12, 15, Order.Status.CREATED, "cond");
        assertNotEquals(result, 0);
    }

    @Test
    public void testGetOrdersByClientID()
    {
        System.out.println("getOrdersByClientID");
        ExchangeService instance = new ExchangeService();
        int clientID = 12;
        List<Order> orders = instance.getOrdersByClientId(clientID);
        assertTrue(orders.size() > 0);

        clientID = -1;
        orders = instance.getOrdersByClientId(clientID);
        assertTrue(orders.size() == 0);
    }

    @Test
    public void testGetOrdersByPerformerID()
    {
        System.out.println("getOrdersByPerformerID");
        ExchangeService instance = new ExchangeService();
        int performerID = 13;
        List<Order> orders = instance.getOrdersByPerformerId(performerID);
        assertTrue(orders.size() > 0);

        performerID = -1;
        orders = instance.getOrdersByPerformerId(performerID);
        assertTrue(orders.size() == 0);
    }

    @Test
    public void testGetOrdersByAgencyID()
    {
        System.out.println("getOrdersByAgencyID");
        ExchangeService instance = new ExchangeService();
        int agencyID = 14;
        List<Order> orders = instance.getOrdersByAgencyId(agencyID);
        assertTrue(orders.size() > 0);

        agencyID = -1;
        orders = instance.getOrdersByAgencyId(agencyID);
        assertTrue(orders.size() == 0);
    }

    @Test
    public void testGetRequestById() throws Exception {
        System.out.println("getRequestById");
        ExchangeService instance = new ExchangeService();

        int requestID = 2;
        Request expResult = new Request(100, 7, "instagram", "sometext");
        expResult.setClientID(2);
        expResult.setID(requestID);
        expResult.setStatus(Request.Status.ASSIGNED);
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
        ExchangeService instance = new ExchangeService();

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
        ExchangeService instance = new ExchangeService();
        String result = instance.getAllRequests();
        assertFalse(result.isEmpty());
    }


    @Test
    public void testAddRequest() {
        System.out.println("addRequest");
        ExchangeService instance = new ExchangeService();
        int result = instance.addRequest(2, 100, 7, "sometext", "instagram", Request.Status.CREATED);
        assertNotEquals(result, 0);
    }

}