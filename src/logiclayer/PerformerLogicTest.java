package logiclayer;

import datalayer.DataGateway;
import entity.Performer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Victor
 */
public class PerformerLogicTest {

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
    public void testAuthPerformerByUserId() throws Exception {
        System.out.println("authPerfomerByUserId");
        PerformerLogic instance = new PerformerLogic();

        int userid = 31;
        Performer expResult = new Performer( "victor", "+79991234567", 14, "vic_performer", "password");
        expResult.setPerformerID(13);
        expResult.setID(31);
        Performer result = instance.authPerformerByUserId(userid);
        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getPerformerID(), result.getPerformerID());
        assertEquals(expResult.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());

        userid = -1;
        expResult = null;
        result = instance.authPerformerByUserId(userid);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllPerformers() throws Exception {
        System.out.println("getAllPerformers");
        PerformerLogic instance = new PerformerLogic();
        String result = instance.getAllPerformers();
        assertFalse(result.isEmpty());
    }

    @Test
    public void getPerformerId() throws Exception {
        System.out.println("getPerformerId");
        int userid = 31;
        PerformerLogic instance = new PerformerLogic();
        int expResult = 13;
        int result = instance.getPerformerId(userid);
        assertEquals(expResult, result);

        userid = 0;
        expResult = -1;
        result = instance.getPerformerId(userid);
        assertEquals(expResult, result);

        userid = -1;
        expResult = -1;
        result = instance.getPerformerId(userid);
        assertEquals(expResult, result);
    }

    @Test
    public void testPerformPaymentToPerformer() throws Exception
    {
        System.out.println("performPaymentToPerformer");
        int userid = 31;
        PerformerLogic instance = new PerformerLogic();
        int performerid = instance.getPerformerId(userid);
        Performer performer = instance.authPerformerByUserId(userid);
        assertNotEquals(null, performer);

        double payment_amount = 100;
        double expResult = payment_amount + performer.getMoneyAmount();

        instance.performPaymentToPerformer(performerid, payment_amount);

        performer = instance.authPerformerByUserId(userid);
        int amount_eq = Double.compare(performer.getMoneyAmount(), expResult);
        assertEquals(amount_eq, 0);
    }

}