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
        Order.Status currentStatus = getOrderStatusById(orderID);
        if (currentStatus == Order.Status.CREATED
            || currentStatus == Order.Status.REJECTED
            || currentStatus == Order.Status.COND_OFFERED)
        {
            og.setOrderConditionsById(orderID, conditions);
            setOrderStatusById(orderID, Order.Status.COND_OFFERED);
        }
    }

    public List<Order> getOrdersByClientId(int clientID)
    {
        String ordersString = og.getOrdersByClientId(clientID);
        return parseOrders(ordersString);
    }

    public List<Order> getOrdersByPerformerId(int performerID)
    {
        String ordersString = og.getOrdersByPerformerId(performerID);
        return parseOrders(ordersString);
    }

    public List<Order> getOrdersByAgencyId(int agencyID)
    {
        String ordersString = og.getOrdersByAgencyId(agencyID);
        return parseOrders(ordersString);
    }

    private List<Order> parseOrders(String ordersString)
    {
        List<Order> orders = new ArrayList<>();

        if(ordersString.isEmpty()){
            return orders;
        }

        String[] fields = ordersString.split("\n");
        int columnCount = 5;
        int count = (fields.length +1)/ columnCount;

        for (int idx =0; idx < count; idx++)
        {
            Order o = new Order();
            o.setID(Integer.parseInt(fields[0 + idx * columnCount]));
            o.setClientID(Integer.parseInt(fields[1 + idx * columnCount]));
            o.setOfferID(Integer.parseInt(fields[2 + idx * columnCount]));
            o.setOrderStatus(Order.Status.values()[Integer.parseInt(fields[3 + idx * columnCount])]);
            if ((4 + idx * columnCount) == fields.length)
                o.setSpecialConditions("");
            else
                o.setSpecialConditions(fields[4 + idx * columnCount]);
            orders.add(o);
        }
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

    public void removeOrder(int order_id){
        if(order_id < 1){
            return;
        }

        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.CREATED
            || currentStatus == Order.Status.REJECTED
            || currentStatus == Order.Status.APPROVED)
        {
            og.removeOrder(order_id);
        }
    }

}
