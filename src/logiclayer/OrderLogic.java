package logiclayer;

import datalayer.OrderGateway;
import entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderLogic {

    OrderGateway og = new OrderGateway();

    public Order getOrderById(int orderID){
        if(orderID < 1){
            return null;
        }

        String orderString = og.getOrderById(orderID);
        if(orderString.isEmpty()){
            return null;
        }

        String[] fields = orderString.split("\n");

        Order o = new Order();
        o.setID(Integer.parseInt(fields[0]));
        o.setClientID(Integer.parseInt(fields[1]));
        o.setOfferID(Integer.parseInt(fields[2]));
        o.setOrderStatus(Order.Status.values()[Integer.parseInt(fields[3])]);
        o.setSpecialConditions(fields[4]);

        return o;
    }

    public Order.Status getOrderStatusById(int orderID){
        if(orderID < 1){
            return null;
        }

        String orderString = og.getOrderStatusById(orderID);
        if(orderString.isEmpty()){
            return null;
        }

        String[] fields = orderString.split("\n");
        return Order.Status.values()[Integer.parseInt(fields[0])];
    }

    public void setOrderStatusById(int orderID, Order.Status status){
        og.setOrderStatusById(orderID, status.getValue());
    }

    public String getOrderConditionsById(int orderID){
        if(orderID < 1){
            return null;
        }

        String orderString = og.getOrderConditionsById(orderID);
        if(orderString.isEmpty()){
            return null;
        }

        String[] fields = orderString.split("\n");
        return fields[0];
    }

    public void setOrderConditionsById(int orderID, String conditions){
        og.setOrderConditionsById(orderID, conditions);
    }

    public List<Order> getOrdersByClientId(int clientID)
    {
        List<Order> orders = new ArrayList<>();


        return orders;
    }

    public String getAllOrders(){
        return og.getAllOrders();
    }

    public int addOrder(int client_id, int offer_id, Order.Status status, String condition){
        if(offer_id < 1 || client_id < 1){
            return -1;
        }

        return og.addOrder(client_id, offer_id, status.getValue(), condition);
    }

}
