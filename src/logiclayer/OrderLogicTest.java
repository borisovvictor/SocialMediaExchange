package logiclayer;

import datalayer.DataGateway;
import entity.Order;
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
        OrderLogic instance = new OrderLogic();

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
        OrderLogic instance = new OrderLogic();

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
        OrderLogic instance = new OrderLogic();
        String result = instance.getAllOrders();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testAddOrder() {
        System.out.println("addOrder");

        OrderLogic instance = new OrderLogic();
        int result = instance.addOrder(12, 15, Order.Status.CREATED, "cond");
        assertNotEquals(result, 0);
    }

    @Test
    public void testGetOrdersByClientID()
    {
        System.out.println("getOrdersByClientID");
        OrderLogic instance = new OrderLogic();
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
        OrderLogic instance = new OrderLogic();
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
        OrderLogic instance = new OrderLogic();
        int agencyID = 14;
        List<Order> orders = instance.getOrdersByAgencyId(agencyID);
        assertTrue(orders.size() > 0);

        agencyID = -1;
        orders = instance.getOrdersByAgencyId(agencyID);
        assertTrue(orders.size() == 0);
    }

}