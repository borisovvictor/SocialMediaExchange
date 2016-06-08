package logiclayer;

import datalayer.*;
import entity.*;
import servicelayer.Util;

import java.util.*;

/**
 * Created by Victor on 08.06.2016.
 */
public class ExchangeService {

    AgencyGateway ag = new AgencyGateway();
    ClientGateway cg = new ClientGateway();
    OfferGateway ofg = new OfferGateway();
    OrderGateway org = new OrderGateway();
    PerformerGateway pg = new PerformerGateway();
    RequestGateway rg = new RequestGateway();
    UserGateway ug = new UserGateway();


    // General logic

    public Object auth(String username, String password){
        int userid = authUser(username, password);

        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        logIn(userid);

        if(authClientByUserId(userid) != null){
            return authClientByUserId(userid);
        }else if(authAgencyByUserId(userid) != null){
            return authAgencyByUserId(userid);
        }else if(authPerformerByUserId(userid) != null){
            return authPerformerByUserId(userid);
        }else{
            return Util.Result.USER_NOT_EXIST; // no user in db
        }
    }

    public Util.Result createNewClient(String name, String username, String password, String phoneNumber){
        if(name.isEmpty() || username.isEmpty() || password.isEmpty()){
            return Util.Result.INVALID_PARAMS;
        }

        if(getUserIdByUsername(username) > 0){
            return Util.Result.USER_ALREADY_EXIST;
        }

        addUser(name, username, password);

        int userid = getUserIdByUsername(username);
        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        addClient(userid, phoneNumber);
        int clientid = getClientId(userid);
        if(clientid < 1) {
            return Util.Result.INTERNAL_ERROR;
        }

        return Util.Result.SUCCEED;
    }

    public Util.Result createNewPerformer(String name, String username, String password, String phoneNumber, int agencyID){
        if(name.isEmpty() || username.isEmpty() || password.isEmpty()){
            return Util.Result.INVALID_PARAMS;
        }

        if(getUserIdByUsername(username) > 0){
            return Util.Result.USER_ALREADY_EXIST;
        }

        addUser(name, username, password);

        int userid = getUserIdByUsername(username);
        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        addPerformer(userid, phoneNumber, agencyID);
        int performerid = getPerformerId(userid);
        if(performerid < 1){
            return Util.Result.INTERNAL_ERROR;
        }

        return Util.Result.SUCCEED;
    }

    public Util.Result createNewAgency(String name, String username, String password){
        if(name.isEmpty() || username.isEmpty() || password.isEmpty()){
            return Util.Result.INVALID_PARAMS;
        }

        if(getUserIdByUsername(username) > 0){
            return Util.Result.USER_ALREADY_EXIST;
        }

        addUser(name, username, password);

        int userid = getUserIdByUsername(username);
        if(userid < 1){
            return Util.Result.USER_NOT_EXIST;
        }

        addAgency(userid);
        int agencyid = getAgencyIdByUserId(userid);
        if(agencyid < 1){
            return Util.Result.INTERNAL_ERROR;
        }

        return Util.Result.SUCCEED;
    }

    // for client only
    public Map<Integer, List<Offer> > findOffers(double price, int period, String socialMedia, String keyWords)
    {
        Map<Integer, List<Offer> > allOffers = new HashMap<>();
        int agency_id = getAgencyIdByUserId(Util.currentLoggedUser);
        if (agency_id < 1)
            return allOffers; // user doesn't exist

        //List<Integer> ids = getAllAgencyIDs();
        //for (Integer id : ids)
        //{
        //    List<Offer> offers = findOffersByAgency(id, price, period, socialMedia, keyWords);
        //    if (offers.size() > 0)
        //        allOffers.put(id, offers);
        //}

        List<Offer> offers = findOffersByAgency(agency_id, price, period, socialMedia, keyWords);
        if (offers.size() > 0)
            allOffers.put(agency_id, offers);

        return allOffers;
    }

    public List<Order> getOrdersByCurrentUser()
    {
        return getOrdersByUserId(Util.currentLoggedUser);
    }

    public List<Order> getOrdersByUserId(int userID)
    {
        int performer_id = getPerformerId(userID);
        if (performer_id > 0)
            return getOrdersByPerformerId(performer_id);

        int client_id = getClientId(userID);
        if (client_id > 0)
            return getOrdersByClientId(client_id);

        int agency_id = getAgencyIdByUserId(userID);
        if (agency_id > 0)
            return getOrdersByAgencyId(agency_id);

        return new ArrayList<>();
    }
    public List<Offer> getOffersByCurrentUser()
    {
        return getOffersByUserId(Util.currentLoggedUser);
    }

    public List<Offer> getOffersByUserId(int userID)
    {
        int performer_id = getPerformerId(userID);
        if (performer_id > 0)
            return getOffersByPerformerId(performer_id);

        return new ArrayList<>();
    }

    public List<Request> getRequestsByCurrentUser()
    {
        return getRequestsByUserId(Util.currentLoggedUser);
    }

    public List<Request> getRequestsByUserId(int userID)
    {
        int client_id = getClientId(userID);
        if (client_id > 0)
            return getRequestsByClientId(client_id);

        int agency_id = getAgencyIdByUserId(userID);
        if (agency_id > 0) {
            return getAllRequestsParsed();
            //return requestLogic.getUnassignedRequests();
        }
        return new ArrayList<>();
    }

    public List<Offer> getOffersByRequestId(int request_id)
    {
        List<Offer> offers = new ArrayList<>();
        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return offers;
        Request request = getRequestById(request_id);
        for (Integer idx : request.getOffersIds())
        {
            Offer offer = getOfferById(idx);
            offers.add(offer);
        }
        return offers;
    }

    public int createOrder(int offer_id)
    {
        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return -1; // user doesn't exist
        return createOrder(client_id, offer_id, "");
    }

    public void removeOrder(int order_id){
        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return; // user doesn't exist

        if(order_id < 1){
            return;
        }

        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.CREATED
                || currentStatus == Order.Status.REJECTED
                || currentStatus == Order.Status.APPROVED)
        {
            org.removeOrder(order_id);
        }
    }

    public Util.Result setConditions(int orderid, String conditions)
    {        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        setOrderConditionsById(orderid, conditions);
        return Util.Result.SUCCEED;
    }

    public Util.Result payForOrder(int order_id)
    {
        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.APPROVED)
            setOrderStatusById(order_id, Order.Status.PAID);
        return Util.Result.SUCCEED;
    }

    public Util.Result confirmOrder(int order_id)
    {
        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.PERFORMED)
            setOrderStatusById(order_id, Order.Status.CONFIRMED);
        return Util.Result.SUCCEED;
    }

    // for performer only
    public Util.Result createOffer(double price, int period, String description, String social_media)
    {
        int performer_id = getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        addOffer(performer_id, price, period, description, social_media);
        return Util.Result.SUCCEED;
    }

    public Util.Result removeOffer(int offer_id)
    {
        int performer_id = getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist

        if(offer_id < 1){
            return Util.Result.INVALID_PARAMS;
        }
        ofg.removeOffer(offer_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result createRequest(double price, int period, String socialMedia, String description)
    {
        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        addRequest(client_id, price, period, description, socialMedia, Request.Status.CREATED);
        return Util.Result.SUCCEED;
    }

    public Util.Result removeRequest(int request_id)
    {
        int client_id = getClientId(Util.currentLoggedUser);
        if (client_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        if(request_id < 1){
         return Util.Result.INVALID_PARAMS;
        }
        //Request.Status status = getRequestStatusById(request_id);
        rg.removeRequest(request_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result approveOrderConditions(int order_id)
    {
        int performer_id = getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.COND_OFFERED
                || currentStatus == Order.Status.REJECTED)
        {
            setOrderStatusById(order_id, Order.Status.APPROVED);
        }
        return Util.Result.SUCCEED;
    }

    public Util.Result rejectOrderConditions(int order_id)
    {
        int performer_id = getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.COND_OFFERED
                || currentStatus == Order.Status.APPROVED)
        {
            setOrderStatusById(order_id, Order.Status.REJECTED);
        }
        return Util.Result.SUCCEED;
    }

    public Util.Result performOrder(int order_id)
    {
        int performer_id = getPerformerId(Util.currentLoggedUser);
        if (performer_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.PAYMENT_CONFIRMED)
        {
            setOrderStatusById(order_id, Order.Status.PERFORMED);
        }
        return Util.Result.SUCCEED;
    }

    // for agency only
    public Util.Result confirmOrderPayment(int order_id)
    {
        int agency_id = getAgencyIdByUserId(Util.currentLoggedUser);
        if (agency_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        confirmPayment(order_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result performOrderPayment(int order_id)
    {
        int agency_id = getAgencyIdByUserId(Util.currentLoggedUser);
        if (agency_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        performPaymentToPerformer(order_id);
        return Util.Result.SUCCEED;
    }

    public Util.Result addOfferToRequest(int request_id, int offer_id)
    {
        int agency_id = getAgencyIdByUserId(Util.currentLoggedUser);
        if (agency_id < 1)
            return Util.Result.USER_NOT_EXIST; // user doesn't exist
        addOfferIdById(request_id, offer_id);
        setRequestStatusById(request_id, Request.Status.ASSIGNED);
        return Util.Result.SUCCEED;
    }

    private void logIn(int userId)
    {
        Util.currentLoggedUser = userId;
    }

    public void logOut(int userId)
    {
        if (userId < 1)
            return;

        if (userId != Util.currentLoggedUser)
            return;

        Util.currentLoggedUser = 0;
    }

    // Agency logic

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
        return ag.getAllAgencies();
    }

    private void addAgency(int user_id){
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

    public List<Integer> getAllAgencyIDs()
    {
        List<Integer> ids = new ArrayList<>();
        String agency_ids = ag.getAllAgencyIDs();

        String[] fields = agency_ids.split("\n");
        for (String field : fields)
        {
            ids.add(Integer.parseInt(field));
        }

        return ids;
    }

    private List<Offer> findOffersByAgency(int agencyId, double price, int period, String socialMedia, String keyWords)
    {
        List<Offer> allOffers = getOffersByAgencyId(agencyId);
        List<Offer> offers = new ArrayList<>();

        for (Offer o : allOffers)
        {
            if (isOfferSatisfyRequest(o, price, period, socialMedia, keyWords))
                offers.add(o);
        }

        return offers;
    }

    private boolean isOfferSatisfyRequest(Offer offer, double price, int period, String socialMedia, String keyWords)
    {
        if (offer.getPrice() > price)
            return false;
        if (!offer.getSocialMedia().contains(socialMedia))
            return false;
        if (offer.getPeriod() < period)
            return false;

        boolean containKeyWords = false;
        for (String keyWord : keyWords.split(":| |,"))
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

    private void confirmPayment(int order_id)
    {
        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.PAID)
            setOrderStatusById(order_id, Order.Status.PAYMENT_CONFIRMED);
    }

    private void performPaymentToPerformer(int order_id)
    {
        Order.Status currentStatus = getOrderStatusById(order_id);
        if (currentStatus == Order.Status.CONFIRMED)
        {
            Order order = getOrderById(order_id);
            Offer offer = getOfferById(order.getOfferID());
            double paymentAmount = 0.9 * offer.getPrice();
            performPaymentToPerformer(offer.getPerformerID(), paymentAmount);
            setOrderStatusById(order_id, Order.Status.COMPLETED);
        }
    }

    // Client logic

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

    private void addClient(int user_id, String phoneNumber){
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

    private int createOrder(int client_id, int offer_id, String conditions)
    {
        return addOrder(client_id, offer_id, Order.Status.CREATED, conditions);
    }

    // Offer logic


    private Offer getOfferById(int offerID){
        if(offerID < 1){
            return null;
        }

        String offerString = ofg.getOfferById(offerID);
        if(offerString.isEmpty()){
            return null;
        }

        String[] fields = offerString.split("\n");

        Offer o = new Offer();
        o.setID(Integer.parseInt(fields[0]));
        o.setPerformerID(Integer.parseInt(fields[1]));
        o.setPrice(Double.parseDouble(fields[2]));
        o.setPeriod(Integer.parseInt(fields[3]));
        o.setDescription(fields[4]);
        o.setSocialMedia(fields[5]);

        return o;
    }

    private List<Offer> getOffersByAgencyId(int agency_id)
    {
        List<Offer> offers = new ArrayList<>();

        String offersString = ofg.getOffersByAgencyId(agency_id);

        if(offersString.isEmpty()){
            return offers;
        }

        String[] fields = offersString.split("\n");
        int columnCount = 6;
        int count = fields.length / columnCount;

        for (int idx =0; idx < count; idx++)
        {
            Offer o = new Offer();
            o.setID(Integer.parseInt(fields[0 + idx * columnCount]));
            o.setPerformerID(Integer.parseInt(fields[1 + idx * columnCount]));
            o.setPrice(Double.parseDouble(fields[2 + idx * columnCount]));
            o.setPeriod(Integer.parseInt(fields[3 + idx * columnCount]));
            o.setDescription(fields[4 + idx * columnCount]);
            o.setSocialMedia(fields[5 + idx * columnCount]);
            offers.add(o);
        }

        return offers;
    }

    private List<Offer> getOffersByPerformerId(int performer_id)
    {
        List<Offer> offers = new ArrayList<>();

        String offersString = ofg.getOffersByPerformerId(performer_id);

        if(offersString.isEmpty()){
            return offers;
        }

        String[] fields = offersString.split("\n");
        int columnCount = 6;
        int count = fields.length / columnCount;

        for (int idx =0; idx < count; idx++)
        {
            Offer o = new Offer();
            o.setID(Integer.parseInt(fields[0 + idx * columnCount]));
            o.setPerformerID(Integer.parseInt(fields[1 + idx * columnCount]));
            o.setPrice(Double.parseDouble(fields[2 + idx * columnCount]));
            o.setPeriod(Integer.parseInt(fields[3 + idx * columnCount]));
            o.setDescription(fields[4 + idx * columnCount]);
            o.setSocialMedia(fields[5 + idx * columnCount]);
            offers.add(o);
        }

        return offers;
    }

    private String getAllOffers(){
        return ofg.getAllOffers();
    }

    private int addOffer(int perfomer_id, double price, int period, String description, String socail_media){
        if(perfomer_id < 1){
            return -1;
        }

        return ofg.addOffer(perfomer_id, price, period, description, socail_media);
    }

    // Order logic

    public Order getOrderById(int orderID){
        if(orderID < 1){
            return null;
        }

        String orderString = org.getOrderById(orderID);
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

        String orderString = org.getOrderStatusById(orderID);
        if(orderString.isEmpty()){
            return null;
        }

        String[] fields = orderString.split("\n");
        return Order.Status.values()[Integer.parseInt(fields[0])];
    }

    public void setOrderStatusById(int orderID, Order.Status status){
        org.setOrderStatusById(orderID, status.getValue());
    }

    private String getOrderConditionsById(int orderID){
        if(orderID < 1){
            return null;
        }

        String orderString = org.getOrderConditionsById(orderID);
        if(orderString.isEmpty()){
            return null;
        }

        String[] fields = orderString.split("\n");
        return fields[0];
    }

    private void setOrderConditionsById(int orderID, String conditions){
        Order.Status currentStatus = getOrderStatusById(orderID);
        if (currentStatus == Order.Status.CREATED
                || currentStatus == Order.Status.REJECTED
                || currentStatus == Order.Status.COND_OFFERED)
        {
            org.setOrderConditionsById(orderID, conditions);
            setOrderStatusById(orderID, Order.Status.COND_OFFERED);
        }
    }

    public List<Order> getOrdersByClientId(int clientID)
    {
        String ordersString = org.getOrdersByClientId(clientID);
        return parseOrders(ordersString);
    }

    public List<Order> getOrdersByPerformerId(int performerID)
    {
        String ordersString = org.getOrdersByPerformerId(performerID);
        return parseOrders(ordersString);
    }

    public List<Order> getOrdersByAgencyId(int agencyID)
    {
        String ordersString = org.getOrdersByAgencyId(agencyID);
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
        return org.getAllOrders();
    }

    public int addOrder(int client_id, int offer_id, Order.Status status, String condition){
        if(offer_id < 1 || client_id < 1){
            return -1;
        }

        return org.addOrder(client_id, offer_id, status.getValue(), condition);
    }



    // Performer logic

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

    private void addPerformer(int user_id, String phoneNumber, int agencyID){
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


    public void performPaymentToPerformer(int performer_id, double money_amount)
    {
        pg.addPerformerMoneyAmount(performer_id, money_amount);
    }

    // Request logic

    public Request getRequestById(int requestID){
        if(requestID < 1){
            return null;
        }

        String requestString = rg.getRequestById(requestID);
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

        String requestString = rg.getRequestStatusById(requestID);
        if(requestString.isEmpty()){
            return null;
        }

        String[] fields = requestString.split("\n");
        return Request.Status.values()[Integer.parseInt(fields[0])];
    }

    public void setRequestStatusById(int requestID, Request.Status status){
        rg.setRequestStatusById(requestID, status.getValue());
    }

    private List<Request> getRequestsByClientId(int clientID)
    {
        String requestsString = rg.getRequestsByClientId(clientID);
        return parseRequests(requestsString);
    }

//    private List<Request> getRequestsByAgencyId(int performerID)
//    {
//        String requestsString = og.getRequestsByPerformerId(performerID);
//        return parseRequests(requestsString);
//    }

    private List<Request> getUnassignedRequests()
    {
        String requestsString = rg.getRequestsByStatus(Request.Status.CREATED.getValue());
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
        return rg.getAllRequests();
    }


    private List<Request> getAllRequestsParsed()
    {
        String requestsString = rg.getAllRequests();
        return parseRequests(requestsString);
    }



    private Set<Integer> getOfferIDsById(int requestID) {
        if (requestID < 1) {
            return null;
        }

        String requestString = rg.getOfferIDsById(requestID);
        return OfferIDsFromString(requestString);
    }

    private void setOfferIDsById(int requestID, Set<Integer> offer_ids){
        rg.setOfferIDsById(requestID, OfferIDsToString(offer_ids));
    }

    private void addOfferIdById(int requestID, Integer offer_id){
        Request request = getRequestById(requestID);
        if (request == null)
            return;

        Set<Integer> offer_ids = request.getOffersIds();
        offer_ids.add(offer_id);

        rg.setOfferIDsById(requestID, OfferIDsToString(offer_ids));
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

        return rg.addRequest(client_id, price, period, description, socialMedia, status.getValue());
    }

    // User logic

    public int authUser(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return -1;
        }

        return ug.authUser(username, password);
    }

    private int addUser(String name, String username, String password){
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return -1; // wrong parameters
        }

        ug.addUser(name, username, password);

        return 0;
    }

    public int getUserIdByUsername(String username){
        return ug.getUserIdByUsername(username);
    }


}
