package logiclayer;

import datalayer.RequestGateway;
import entity.Request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RequestLogic {

    RequestGateway og = new RequestGateway();

    public Request getRequestById(int requestID){
        if(requestID < 1){
            return null;
        }

        String requestString = og.getRequestById(requestID);
        if(requestString.isEmpty()){
            return null;
        }

        String[] fields = requestString.split("\n");

        Request r = new Request();
        r.setID(Integer.parseInt(fields[0]));
        r.setClientID(Integer.parseInt(fields[1]));
        r.setPrice(Double.parseDouble(fields[2]));
        r.setPeriod(Integer.parseInt(fields[3]));
        r.setDescription(fields[4]);
        r.setSocialMedia(fields[5]);
        r.setStatus(Request.Status.values()[Integer.parseInt(fields[6])]);
        if (fields.length < 8)
            r.setOffersIds(OfferIDsFromString(""));
        else
            r.setOffersIds(OfferIDsFromString(fields[7]));

        return r;
    }

    public Request.Status getRequestStatusById(int requestID){
        if(requestID < 1){
            return null;
        }

        String requestString = og.getRequestStatusById(requestID);
        if(requestString.isEmpty()){
            return null;
        }

        String[] fields = requestString.split("\n");
        return Request.Status.values()[Integer.parseInt(fields[0])];
    }

    public void setRequestStatusById(int requestID, Request.Status status){
        og.setRequestStatusById(requestID, status.getValue());
    }

    public List<Request> getRequestsByClientId(int clientID)
    {
        String requestsString = og.getRequestsByClientId(clientID);
        return parseRequests(requestsString);
    }

//    public List<Request> getRequestsByAgencyId(int performerID)
//    {
//        String requestsString = og.getRequestsByPerformerId(performerID);
//        return parseRequests(requestsString);
//    }

    public List<Request> getUnassignedRequests()
    {
        String requestsString = og.getRequestsByStatus(Request.Status.CREATED.getValue());
        //String requestsString = og.getRequestsByAgencyId(agencyID);
        return parseRequests(requestsString);
    }

    private List<Request> parseRequests(String requestsString)
    {
        List<Request> requests = new ArrayList<>();

        if(requestsString.isEmpty()){
            return requests;
        }

        String[] fields = requestsString.split("\n");
        int columnCount = 8;
        int count = (fields.length + 1) / columnCount; // fix. if last field is empty, split ignore it

        for (int idx =0; idx < count; idx++)
        {
            Request r = new Request();
            r.setID(Integer.parseInt(fields[0 + idx * columnCount]));
            r.setClientID(Integer.parseInt(fields[1 + idx * columnCount]));
            r.setPrice(Double.parseDouble(fields[2 + idx * columnCount]));
            r.setPeriod(Integer.parseInt(fields[3 + idx * columnCount]));
            r.setDescription(fields[4 + idx * columnCount]);
            r.setSocialMedia(fields[5 + idx * columnCount]);
            r.setStatus(Request.Status.values()[Integer.parseInt(fields[6 + idx * columnCount])]);
            if (7 + idx * columnCount == fields.length)
                r.setOffersIds(OfferIDsFromString(""));
            else
                r.setOffersIds(OfferIDsFromString(fields[7 + idx * columnCount]));

            requests.add(r);
        }
        return requests;
    }

    public String getAllRequests(){
        return og.getAllRequests();
    }

    public Set<Integer> getOfferIDsById(int requestID) {
        if (requestID < 1) {
            return null;
        }

        String requestString = og.getOfferIDsById(requestID);
        return OfferIDsFromString(requestString);
    }

    public void setOfferIDsById(int requestID, Set<Integer> offer_ids){
        og.setOfferIDsById(requestID, OfferIDsToString(offer_ids));
    }

    public void addOfferIdById(int requestID, Integer offer_id){
        Request request = getRequestById(requestID);
        if (request == null)
            return;

        Set<Integer> offer_ids = request.getOffersIds();
        offer_ids.add(offer_id);

        og.setOfferIDsById(requestID, OfferIDsToString(offer_ids));
    }

    private String OfferIDsToString(Set<Integer> offer_ids)
    {
        String ids = "";
        for (Integer idx : offer_ids)
        {
            ids += idx.toString() + ",";
        }
        return ids;
    }

    private Set<Integer> OfferIDsFromString(String offer_ids)
    {
        Set<Integer> result = new HashSet<>();
        String[] fields = offer_ids.split(",");
        for (String field : fields) {
            if (!field.isEmpty())
                result.add(Integer.parseInt(field));
        }
        return result;
    }

    public int addRequest(int client_id, double price, int period,  String description,
                          String socialMedia, Request.Status status){
        if(client_id < 1){
            return -1;
        }

        return og.addRequest(client_id, price, period, description, socialMedia, status.getValue());
    }

    public void removeRequest(int request_id){
        if(request_id < 1){
            return;
        }
        //Request.Status status = getRequestStatusById(request_id);
        og.removeRequest(request_id);
    }

}
