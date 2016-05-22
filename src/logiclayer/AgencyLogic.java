package logiclayer;

import com.sun.xml.internal.ws.api.message.Packet;
import datalayer.AgencyGateway;
import entity.*;

import java.util.ArrayList;
import java.util.List;

public class AgencyLogic {
    AgencyGateway ag = new AgencyGateway();
    OfferLogic ofl = new OfferLogic();
    OrderLogic orl = new OrderLogic();
    PerformerLogic prl = new PerformerLogic();

    public Agency authAgencyByUserId(int userID){
        if(userID < 1){
            return null;
        }

        String agencyString = ag.getAgencyByUserID(userID);
        if(agencyString.isEmpty()){
            return null;
        }

        String[] fields = agencyString.split("\n");

        Agency a = new Agency();
        a.setAgencyID(Integer.parseInt(fields[0]));
        a.setID(Integer.parseInt(fields[1]));
        a.setName(fields[3]);
        a.setUsername(fields[4]);
        a.setPassword(fields[5]);

        return a;
    }

    public String getAllAgencies(){
        return ag.getAllAgencys();
    }

    public void addAgency(int user_id){
        if(user_id < 1){
            return;
        }

        ag.addAgency(user_id);
    }

    public int getAgencyIdByUserId(int userID){
        if(userID < 1){
            return -1;
        }

        return ag.getAgencyIdByUserId(userID);
    }

    public List<Offer> findOffersByAgency(int agencyId, Request request)
    {
        List<Offer> allOffers = ofl.getOffersByAgencyId(agencyId);
        List<Offer> offers = new ArrayList<>();

        for (Offer o : allOffers)
        {
          if (isOfferSatisfyRequest(o, request))
              offers.add(o);
        }

        return offers;
    }

    public boolean isOfferSatisfyRequest(Offer offer, Request request)
    {
        if (offer.getPrice() > request.getPrice())
            return false;
        if (!offer.getSocialMedia().contains(request.getSocialMedia()))
            return false;
        if (offer.getPeriod() < request.getPeriod())
            return false;

        boolean containKeyWords = false;
        for (String keyWord : request.getKeyWords())
        {
            if (offer.getDescription().contains(keyWord))
            {
                containKeyWords = true;
                break;
            }
        }

        if (!containKeyWords)
            return false;

        return true;
    }

    public void receivePaymentFromClient(int order_id)
    {
        orl.setOrderStatusById(order_id, Order.Status.PAID);
    }

    public void performPaymentToPerformer(int performer_id, int order_id)
    {
        Order order = orl.getOrderById(order_id);
        Offer offer = ofl.getOfferById(order.getOfferID());
        double paymentAmount = 0.9 * offer.getPrice();
        prl.performPaymentToPerformer(performer_id, paymentAmount);
        orl.setOrderStatusById(order_id, Order.Status.COMPLETED);
    }


}
