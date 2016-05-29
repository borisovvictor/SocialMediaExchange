package logiclayer;

import datalayer.ClientGateway;
import datalayer.OrderGateway;
import entity.Client;
import entity.Offer;
import entity.Order;
import entity.Request;

/**
 * Created by Victor on 15.05.2016.
 */
public class ClientLogic {
    ClientGateway cg = new ClientGateway();
    OrderLogic ol = new OrderLogic();
    AgencyLogic al = new AgencyLogic();

    public Client authClientByUserId(int userID){
        if(userID < 1){
            return null;
        }

        String clientString = cg.getClientByUserID(userID);
        if(clientString.isEmpty()){
            return null;
        }

        String[] fields = clientString.split("\n");

        Client c = new Client();
        c.setClientID(Integer.parseInt(fields[0]));
        c.setID(Integer.parseInt(fields[1]));
        c.setPhoneNumber(fields[2]);
        c.setName(fields[4]);
        c.setUsername(fields[5]);
        c.setPassword(fields[6]);

        return c;
    }

    public String getAllClients(){
        return cg.getAllClients();
    }

    public void addClient(int user_id, String phoneNumber){
        if(user_id < 1){
            return;
        }

        cg.addClient(user_id, phoneNumber);
    }

    public int getClientId(int userID){
        if(userID < 1){
            return -1;
        }

        return cg.getClientIdByUserId(userID);
    }

    public Request createRequest(double price, int period, String socialMedia, String[] keyWords)
    {
        return new Request(price, period, socialMedia, keyWords);
    }

    public int createOrder(int client_id, int offer_id, String conditions)
    {
        return ol.addOrder(client_id, offer_id, Order.Status.CREATED, conditions);
    }

    public void removeOrder(int order_id)
    {
        ol.removeOrder(order_id);
    }

    public void setConditions(int orderid, String conditions)
    {
        ol.setOrderConditionsById(orderid, conditions);
    }

    public void payForOrder(int orderid)
    {
        Order.Status currentStatus = ol.getOrderStatusById(orderid);
        if (currentStatus == Order.Status.APPROVED)
            ol.setOrderStatusById(orderid, Order.Status.PAID);
    }

    public void confirmOrder(int orderid)
    {
        Order.Status currentStatus = ol.getOrderStatusById(orderid);
        if (currentStatus == Order.Status.PERFORMED)
            ol.setOrderStatusById(orderid, Order.Status.CONFIRMED);
    }


    //public
}
