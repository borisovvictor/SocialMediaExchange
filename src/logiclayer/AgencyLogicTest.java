package logiclayer;

import datalayer.DataGateway;
import entity.Agency;
import entity.Offer;
import entity.Request;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class AgencyLogicTest {
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
    public void testAuthAgencyByUserId() {
        System.out.println("authAgencyByUserId");
        AgencyLogic instance = new AgencyLogic();

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
        AgencyLogic instance = new AgencyLogic();
        String result = instance.getAllAgencies();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetAgencyId() {
        System.out.println("getAgencyId");
        int userid = 41;
        AgencyLogic instance = new AgencyLogic();
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
        System.out.println("findOffersByAgencyId");

        int agencyid = 14;
        Request rq = new Request(200, 5, "insta", new String[] {"sport"});

        AgencyLogic instance = new AgencyLogic();

        List<Offer> result = instance.findOffersByAgency(agencyid, rq);
        assertEquals(1, result.size());

        Offer offer_result = result.get(0);
        assertNotEquals(null, offer_result);
        assertEquals(offer_result.getPeriod(), 7);
        int price_eq = Double.compare(offer_result.getPrice(), 150);
        assertEquals(price_eq, 0);

        agencyid = 0;
        result = instance.findOffersByAgency(agencyid, rq);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllAgencyIDs()
    {
        System.out.println("getAllAgencyIDs");

        AgencyLogic instance = new AgencyLogic();

        List<Integer> result = instance.getAllAgencyIDs();
        assertNotEquals(0, result.size());
    }




}