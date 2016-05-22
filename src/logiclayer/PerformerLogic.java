package logiclayer;

import datalayer.PerformerGateway;
import datalayer.OfferGateway;
import entity.Order;
import entity.Performer;

/**
 * Created by Victor
 */
public class PerformerLogic {
    PerformerGateway pg = new PerformerGateway();
    OfferGateway ofg = new OfferGateway();
    OrderLogic orl = new OrderLogic();


    public Performer authPerformerByUserId(int userID){
        if(userID < 1){
            return null;
        }

        String performerString = pg.getPerformerByUserID(userID);
        if(performerString.isEmpty()){
            return null;
        }

        String[] fields = performerString.split("\n");

        Performer c = new Performer();
        c.setPerformerID(Integer.parseInt(fields[0]));
        c.setID(Integer.parseInt(fields[1]));
        c.setPhoneNumber(fields[2]);
        c.setAgencyID(Integer.parseInt(fields[3]));
        c.setMoneyAmount(Double.parseDouble(fields[4]));
        c.setName(fields[6]);
        c.setUsername(fields[7]);
        c.setPassword(fields[8]);

        return c;
    }

    public String getAllPerformers(){
        return pg.getAllPerformers();
    }

    public void addPerformer(int user_id, String phoneNumber, int agencyID){
        if(user_id < 1){
            return;
        }

        pg.addPerformer(user_id, phoneNumber, agencyID);
    }

    public int getPerformerId(int userID){
        if(userID < 1){
            return -1;
        }

        return pg.getPerformerIdByUserId(userID);
    }

    public int addOffer(int performerid, double price, int period, String description, String socialMedia)
    {
        return ofg.addOffer(performerid, price, period, description, socialMedia);
    }

    public void approveOrderConditions(int orderid)
    {
        orl.setOrderStatusById(orderid, Order.Status.APPROVED);
    }

    public void rejectOrderConditions(int orderid)
    {
        orl.setOrderStatusById(orderid, Order.Status.REJECTED);
    }

    public void performOrder(int orderid)
    {
        orl.setOrderStatusById(orderid, Order.Status.COMPLETED);
    }

    public void performPaymentToPerformer(int performer_id, double money_amount)
    {
        pg.addPerformerMoneyAmount(performer_id, money_amount);
    }




}
