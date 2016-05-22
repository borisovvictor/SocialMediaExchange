package entity;

/**
 * Created by Victor
 */
public class Performer extends User {

    private int performerID;
    private String phoneNumber;
    private int agencyID;
    private double moneyAmount;

    public Performer() {
        super("", "", "");
    }

    public Performer(String name, String phoneNumber, int agencyID, String username, String password) {
        super(name, username, password);
        this.phoneNumber = phoneNumber;
        this.agencyID = agencyID;
        this.moneyAmount = 0;
    }

    public int getPerformerID() { return performerID; }

    public void setPerformerID(int performerID) { this.performerID = performerID; }

    public String getPhoneNumber() { return this.phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getAgencyID() { return agencyID; }

    public void setAgencyID(int agencyID) { this.agencyID = agencyID; }

    public double getMoneyAmount() { return moneyAmount; }

    public void setMoneyAmount(double moneyAmount) { this.moneyAmount = moneyAmount; }
}
