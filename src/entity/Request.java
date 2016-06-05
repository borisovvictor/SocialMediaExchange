package entity;

import java.util.*;

public class Request {

    public enum Status
    {
        NONE(0),                // отсутствует
        CREATED(1),             // создан
        ASSIGNED(2),            // запрос прикреплен к агентсву
        COMPLETED(3);           // завершен

        private int value;
        private static Map<Integer, Request.Status> map = new HashMap<>();

        private Status(int value) {
            this.value = value;
        }

        static {
            for (Request.Status status : Request.Status.values()) {
                map.put(status.value, status);
            }
        }

        public static Request.Status valueOf(int status) {
            return map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    private int id;
    private int client_id;
    private double price;
    private int period;
    private String socialMedia;
    private String description;
    private Status status;
    private Set<Integer> offersIds;

    public Request()
    {
    }

    public Request(double price, int period, String socialMedia, String description)
    {
        this.price = price;
        this.period = period;
        this.socialMedia = socialMedia;
        this.description = description;
        this.status = Status.NONE;
        offersIds = new HashSet<>();
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public int getClientID() {
        return client_id;
    }

    public void setClientID(int client_id) {
        this.client_id = client_id;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getPeriod() { return period; }

    public void setPeriod(int period) { this.period = period; }

    public String getSocialMedia() { return socialMedia; }

    public void setSocialMedia(String socialMedia) { this.socialMedia = socialMedia; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public Set<Integer> getOffersIds() { return offersIds; }

    public void setOffersIds(Set<Integer> offersIds) { this.offersIds = offersIds; }
}
