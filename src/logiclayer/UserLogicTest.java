package logiclayer;

import datalayer.DataGateway;
import entity.Agency;
import entity.Client;
import entity.Performer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

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
        ExchangeService instance = new ExchangeService();
        int expResult = -1;
        int result = instance.authUser(username, password);
        assertEquals(expResult, result);
    }

    @Test
    public void getUserIdByUsername() throws Exception {
        System.out.println("getUserIdByLogin");
        String username = "vic_performer";
        ExchangeService instance = new ExchangeService();
        int expResult = 31;
        int result = instance.getUserIdByUsername(username);
        assertEquals(expResult, result);
    }

    @Test
    public void testAuthAgencyByUserId() {
        System.out.println("authAgencyByUserId");
        ExchangeService instance = new ExchangeService();

        int userid = 41;
        Agency expResult = new Agency("victor", "vic_agency", "password");
        expResult.setAgencyID(14);
        expResult.setID(41);
        Agency result = instance.authAgencyByUserId(userid);
        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getAgencyID(), result.getAgencyID());
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());

        userid = -1;
        expResult = null;
        result = instance.authAgencyByUserId(userid);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllAgencys() {
        System.out.println("getAllAgencies");
        ExchangeService instance = new ExchangeService();
        String result = instance.getAllAgencies();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetAgencyId() {
        System.out.println("getAgencyId");
        int userid = 41;
        ExchangeService instance = new ExchangeService();
        int expResult = 14;
        int result = instance.getAgencyIdByUserId(userid);
        assertEquals(expResult, result);

        userid = 0;
        expResult = -1;
        result = instance.getAgencyIdByUserId(userid);
        assertEquals(expResult, result);

        userid = -1;
        expResult = -1;
        result = instance.getAgencyIdByUserId(userid);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindOffersByAgencyId()
    {
        /*System.out.println("findOffersByAgencyId");

        int agencyid = 14;
        Request rq = new Request(200, 5, "insta", new String[] {"sport"});

        ExchangeService instance = new ExchangeService();

        List<Offer> result = instance.findOffersByAgency(agencyid, rq);
        assertEquals(1, result.size());

        Offer offer_result = result.get(0);
        assertNotEquals(null, offer_result);
        assertEquals(offer_result.getPeriod(), 7);
        int price_eq = Double.compare(offer_result.getPrice(), 150);
        assertEquals(price_eq, 0);

        agencyid = 0;
        result = instance.findOffersByAgency(agencyid, rq);
        assertEquals(0, result.size());*/
    }

    @Test
    public void testGetAllAgencyIDs()
    {
        System.out.println("getAllAgencyIDs");

        ExchangeService instance = new ExchangeService();

        List<Integer> result = instance.getAllAgencyIDs();
        assertNotEquals(0, result.size());
    }

    @Test
    public void testAuthClientByUserId() {
        System.out.println("authClientByUserId");
        ExchangeService instance = new ExchangeService();

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
        ExchangeService instance = new ExchangeService();
        String result = instance.getAllClients();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetClientId() {
        System.out.println("getClientId");
        int userid = 21;
        ExchangeService instance = new ExchangeService();
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

    @Test
    public void testAuthPerformerByUserId() throws Exception {
        System.out.println("authPerfomerByUserId");
        ExchangeService instance = new ExchangeService();

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
        ExchangeService instance = new ExchangeService();
        String result = instance.getAllPerformers();
        assertFalse(result.isEmpty());
    }

    @Test
    public void getPerformerId() throws Exception {
        System.out.println("getPerformerId");
        int userid = 31;
        ExchangeService instance = new ExchangeService();
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
        ExchangeService instance = new ExchangeService();
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