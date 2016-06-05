package datalayer;

import entity.Request;

/**
 * Created by Victor
 */
public class RequestGateway {

    public int addRequest(int client_id, double price, int period,
                        String description, String socialMedia, int status)
    {
        String tmp = DataGateway.request("insert into requests (client_id, price, period, description, social_media, status, offer_ids) values ("
                + client_id + "," + price + "," + period
                + ",\'" + description + "\',\'" + socialMedia + "\'," + status + ",\'\') returning id");

        return Integer.parseInt(tmp.split("\n")[0]);
    }

    public void removeRequest(int request_id)
    {
        DataGateway.update("delete from requests where id=" + request_id);
    }

    public String getRequestById(int requestid){
        if(requestid < 1){
            return "";
        }

        return DataGateway.request(
                "select * from requests where requests.id = " + requestid);
    }

//    public String getRequestsByAgencyId(int agencyid){
//        if(agencyid < 1){
//            return "";
//        }
//
//        return DataGateway.request(
//                "select requests.id,requests.performer_id,requests.price,requests.period,requests.description,requests.social_media "
//                        + "from (requests join performers on requests.performer_id = performers.id and performers.agency_id = " + agencyid + ")");
//    }
//
    public String getRequestsByClientId(int clientid){
        if(clientid < 1){
            return "";
        }

        return DataGateway.request(
                "select * from requests where requests.client_id = " + clientid);
    }

    public String getRequestStatusById(int requestid){
        if(requestid < 1){
            return "";
        }

        return DataGateway.request(
                "select status from requests where requests.id = " + requestid);
    }

    public void setRequestStatusById(int requestid, int status){
        if(requestid < 1){
            return;
        }

        DataGateway.request(
                "update requests set status=" + status + " where requests.id = " + requestid);
    }

    public String getOfferIDsById(int requestid){
        if(requestid < 1){
            return "";
        }

        return DataGateway.request(
                "select offer_ids from requests where requests.id = " + requestid);
    }

    public void setOfferIDsById(int requestid, String offer_ids){
        if(requestid < 1){
            return;
        }

        DataGateway.update(
                "update requests set offer_ids=\'" + offer_ids + "\' where requests.id = " + requestid);
    }

    public String getRequestsByStatus(int status){
        return DataGateway.request("select * from requests where requests.status = " + status);
    }

    public String getAllRequests(){
        return DataGateway.request("select * from requests");
    }


}
