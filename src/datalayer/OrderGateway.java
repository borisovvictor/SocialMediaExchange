package datalayer;

import entity.Order;

public class OrderGateway {

    public int addOrder(int client_id, int offer_id, int status, String condition)
    {
        String tmp = DataGateway.request("insert into orders (client_id, offer_id, status, conditions) values ("
                + client_id + "," + offer_id + "," + status + ",\'" + condition + "\') returning id");

        return Integer.parseInt(tmp.split("\n")[0]);
    }

    public void removeOrder(int order_id)
    {
        DataGateway.update("delete from orders where id=" + order_id);
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

        DataGateway.update(
                "update orders set conditions=\'" + condition + "\' where orders.id = " + orderid);
    }

    public String getAllOrders(){
        return DataGateway.request("select * from orders");
    }

    public String getOrdersByClientId(int clientid){
        if(clientid < 1){
            return "";
        }

        return DataGateway.request(
                "select * from orders where orders.client_id=" + clientid);
    }

    public String getOrdersByPerformerId(int performerid) {
        if (performerid < 1) {
            return "";
        }
        return DataGateway.request(
                "select orders.id,orders.client_id,orders.offer_id,orders.status,orders.conditions "
                        + "from (orders join offers on orders.offer_id = offers.id and offers.performer_id = " + performerid + ")");
    }

    public String getOrdersByAgencyId(int agencyid) {
        if (agencyid < 1) {
            return "";
        }
        return DataGateway.request(
                "select orders.id,orders.client_id,orders.offer_id,orders.status,orders.conditions"
                + " from ((orders join offers on orders.offer_id = offers.id)"
                + " join performers on offers.performer_id = performers.id and performers.agency_id =" + agencyid + ")");
    }

}
