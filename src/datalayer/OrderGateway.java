package datalayer;

import entity.Order;

public class OrderGateway {

    public int addOrder(int client_id, int offer_id, int status, String condition)
    {
        String tmp = DataGateway.request("insert into orders (client_id, offer_id, status, conditions) values ("
                + client_id + "," + offer_id + "," + status + ",\'" + condition + "\') returning id");

        return Integer.parseInt(tmp.split("\n")[0]);
    }

    public String getOrderById(int orderid){
        if(orderid < 1){
            return "";
        }

        return DataGateway.request(
                "select * from orders where orders.id = " + orderid);
    }

    public String getOrderStatusById(int orderid){
        if(orderid < 1){
            return "";
        }

        return DataGateway.request(
                "select status from orders where orders.id = " + orderid);
    }

    public void setOrderStatusById(int orderid, int status){
        if(orderid < 1){
            return;
        }

        DataGateway.request(
                "update orders set status=" + status + " where orders.id = " + orderid);
    }

    public String getOrderConditionsById(int orderid){
        if(orderid < 1){
            return "";
        }

        return DataGateway.request(
                "select conditions from orders where orders.id = " + orderid);
    }

    public void setOrderConditionsById(int orderid, String condition){
        if(orderid < 1){
            return;
        }

        DataGateway.request(
                "update orders set conditions=" + condition + " where orders.id = " + orderid);
    }

    public String getAllOrders(){
        return DataGateway.request("select * from orders");
    }
}
