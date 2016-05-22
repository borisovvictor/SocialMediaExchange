package entity;

/**
 * Created by Victor
 */
public class Offer {

    private int offerID;
    private int performerID;
    private double price;
    private int period; // in days
    private String description;
    private String socialMedia;

    public Offer(double price, int period, String description, String socialMedia)
    {
        this.price = price;
        this.period = period;
        this.description = description;
        this.socialMedia = socialMedia;
    }

    public Offer()
    {}

    public int getID() { return offerID; }

    public void setID(int offerID) { this.offerID = offerID; }

    public int getPerformerID() { return performerID; }

    public void setPerformerID(int performerID) { this.performerID = performerID; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getPeriod() { return period; }

    public void setPeriod(int period) { this.period = period; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getSocialMedia() { return socialMedia; }

    public void setSocialMedia(String socialMedia) { this.socialMedia = socialMedia; }
}
