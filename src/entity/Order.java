package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor
 */
public class Order {

    public enum Status
    {
        NONE(0),       // отсутствует
        CREATED(1),    // создан
        APPROVED(2),   // условия согласованы
        REJECTED(3),   // условия отклонены
        PAID(4),       // оплачен
        PERFORMED(5),  // выполнен
        CONFIRMED(6),  // выполнение подтверждено
        COMPLETED(7);  // завершен

        private int value;
        private static Map<Integer, Status> map = new HashMap<>();

        private Status(int value) {
            this.value = value;
        }

        static {
            for (Status status : Status.values()) {
                map.put(status.value, status);
            }
        }

        public static Status valueOf(int status) {
            return map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    private int orderID;
    private int clientID;
    private int offerID;
    private Status orderStatus;
    private String specialConditions;

    public Order()
    {
        this.offerID = -1;
        this.clientID = -1;
        this.specialConditions = "";
        this.orderStatus = Status.NONE;
    }

    public Order(int clientID, int offerID, String specialConditions)
    {
        this.clientID = clientID;
        this.offerID = offerID;
        this.specialConditions = specialConditions;
        this.orderStatus = Status.NONE;
    }

    public int getClientID() { return clientID; }

    public void setClientID(int clientID) { this.clientID = clientID; }

    public int getOfferID() { return offerID; }

    public void setOfferID(int offerID) { this.offerID = offerID; }

    public int getID() { return orderID; }

    public void setID(int orderID) { this.orderID = orderID; }

    public Status getOrderStatus() { return orderStatus; }

    public void setOrderStatus(Status orderStatus) { this.orderStatus = orderStatus; }

    public String getSpecialConditions() { return specialConditions; }

    public void setSpecialConditions(String specialConditions) { this.specialConditions = specialConditions; }
}
