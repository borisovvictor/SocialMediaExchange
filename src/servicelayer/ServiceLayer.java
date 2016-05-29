package servicelayer;

import entity.Offer;
import entity.Order;
import entity.Request;
import logiclayer.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceLayer {
    public AgencyLogic agencyLogic;
    public ClientLogic clientLogic;
    public OfferLogic offerLogic;
    public OrderLogic orderLogic;
    public PerformerLogic performerLogic;
    public UserLogic userLogic;

    public ServiceLayer()
    {
        agencyLogic = new AgencyLogic();
        clientLogic = new ClientLogic();
        offerLogic = new OfferLogic();
        orderLogic = new OrderLogic();
        performerLogic = new PerformerLogic();
        userLogic = new UserLogic();
    }

    public Object auth(String username, String password){
        int userid = userLogic.authUser(username, password);

        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        userLogic.logIn(userid);

        if(clientLogic.authClientByUserId(userid) != null){
            return clientLogic.authClientByUserId(userid);
        }else if(agencyLogic.authAgencyByUserId(userid) != null){
            return agencyLogic.authAgencyByUserId(userid);
        }else if(performerLogic.authPerformerByUserId(userid) != null){
            return performerLogic.authPerformerByUserId(userid);
        }else{
            return Util.Result.USER_NOT_EXIST; // no user in db
        }
    }

    public Util.Result createNewClient(String name, String username, String password, String phoneNumber){
        if(name.isEmpty() || username.isEmpty() || password.isEmpty()){
            return Util.Result.INVALID_PARAMS;
        }

        if(userLogic.getUserIdByUsername(username) > 0){
            return Util.Result.USER_ALREADY_EXIST;
        }

        userLogic.addUser(name, username, password);

        int userid = userLogic.getUserIdByUsername(username);
        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        clientLogic.addClient(userid, phoneNumber);
        int clientid = clientLogic.getClientId(userid);
        if(clientid < 1) {
            return Util.Result.INTERNAL_ERROR;
        }

        return Util.Result.SUCCEED;
    }

    public Util.Result createNewPerformer(String name, String username, String password, String phoneNumber, int agencyID){
        if(name.isEmpty() || username.isEmpty() || password.isEmpty()){
            return Util.Result.INVALID_PARAMS;
        }

        if(userLogic.getUserIdByUsername(username) > 0){
            return Util.Result.USER_ALREADY_EXIST;
        }

        userLogic.addUser(name, username, password);

        int userid = userLogic.getUserIdByUsername(username);
        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        performerLogic.addPerformer(userid, phoneNumber, agencyID);
        int performerid = performerLogic.getPerformerId(userid);
        if(performerid < 1){
            return Util.Result.INTERNAL_ERROR;
        }

        return Util.Result.SUCCEED;
    }

    public Util.Result createNewAgency(String name, String username, String password){
        if(name.isEmpty() || username.isEmpty() || password.isEmpty()){
            return Util.Result.INVALID_PARAMS;
        }

        if(userLogic.getUserIdByUsername(username) > 0){
            return Util.Result.USER_ALREADY_EXIST;
        }

        userLogic.addUser(name, username, password);

        int userid = userLogic.getUserIdByUsername(username);
        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        agencyLogic.addAgency(userid);
        int agencyid = agencyLogic.getAgencyIdByUserId(userid);
        if(agencyid < 1){
            return Util.Result.INTERNAL_ERROR;
        }

        return Util.Result.SUCCEED;
    }

    // for client only
    public Map<Integer, List<Offer> > findOffers(Request request)
    {
        Map<Integer, List<Offer> > allOffers = new HashMap<>();
        int client_id = clientLogic.getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return allOffers; // user doesn't exist

        List<Integer> ids = agencyLogic.getAllAgencyIDs();
        for (Integer id : ids)
        {
            List<Offer> offers = agencyLogic.findOffersByAgency(id, request);
            if (offers.size() > 0)
                allOffers.put(id, offers);
        }

        return allOffers;
    }

    public List<Order> getOrdersByCurrentUser()
    {
        return getOrdersByUserId(Util.currentLoggedUser);
    }

    public List<Order> getOrdersByUserId(int userID)
    {
        int performer_id = performerLogic.getPerformerId(userID);
        if (performer_id > 0)
            return orderLogic.getOrdersByPerformerId(performer_id);

        int client_id = clientLogic.getClientId(userID);
        if (client_id > 0)
            return orderLogic.getOrdersByClientId(client_id);

        int agency_id = agencyLogic.getAgencyIdByUserId(userID);
        if (agency_id > 0)
            return orderLogic.getOrdersByAgencyId(agency_id);

        return new ArrayList<>();
    }
    public List<Offer> getOffersByCurrentUser()
    {
        return getOffersByUserId(Util.currentLoggedUser);
    }

    public List<Offer> getOffersByUserId(int userID)
    {
        int performer_id = performerLogic.getPerformerId(userID);
        if (performer_id > 0)
            return offerLogic.getOffersByPerformerId(performer_id);

        return new ArrayList<>();
    }


    public int createOrder(int offer_id)
    {
        int client_id = clientLogic.getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return -1; // user doesn't exist
        return clientLogic.createOrder(client_id, offer_id, "");
    }

    public void removeOrder(int order_id)
    {
        int client_id = clientLogic.getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return; // user doesn't exist
        clientLogic.removeOrder(order_id);
    }

    public Util.Result setConditions(int order_id, String conditions)
    {
        int client_id = clientLogic.getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        clientLogic.setConditions(order_id, conditions);
        return Util.Result.SUCCEED;
    }

    public Util.Result payForOrder(int order_id)
    {
        int client_id = clientLogic.getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        clientLogic.payForOrder(order_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result confirmOrder(int order_id)
    {
        int client_id = clientLogic.getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        clientLogic.confirmOrder(order_id);
        return Util.Result.SUCCEED;
    }

    // for performer only
    public Util.Result createOffer(double price, int period, String description, String social_media)
    {
        int performer_id = performerLogic.getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        offerLogic.addOffer(performer_id, price, period, description, social_media);
        return Util.Result.SUCCEED;
    }

    public Util.Result removeOffer(int offer_id)
    {
        int performer_id = performerLogic.getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        offerLogic.removeOffer(offer_id);
        return Util.Result.SUCCEED;
    }

    public Request createRequest(double price, int period, String socialMedia, String keyWords)
    {
        String[] words = keyWords.split(":| |,");
        return clientLogic.createRequest(price, period, socialMedia, words);
    }

    public Util.Result approveOrderConditions(int order_id)
    {
        int performer_id = performerLogic.getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        performerLogic.approveOrderConditions(order_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result rejectOrderConditions(int order_id)
    {
        int performer_id = performerLogic.getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        performerLogic.rejectOrderConditions(order_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result performOrder(int order_id)
    {
        int performer_id = performerLogic.getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        performerLogic.performOrder(order_id);
        return Util.Result.SUCCEED;
    }

    // for agency only
    public Util.Result confirmOrderPayment(int order_id)
    {
        int agency_id = agencyLogic.getAgencyIdByUserId(Util.currentLoggedUser);
        if (agency_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        agencyLogic.confirmPayment(order_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result performOrderPayment(int order_id)
    {
        int agency_id = agencyLogic.getAgencyIdByUserId(Util.currentLoggedUser);
        if (agency_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        agencyLogic.performPaymentToPerformer(order_id);
        return Util.Result.SUCCEED;
    }
}