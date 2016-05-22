package entity;

/**
 * Created by Victor
 */
public class Agency extends User {

    private int agencyID;

    public Agency() {
        super("", "", "");
    }

    public Agency(String name, String username, String password) {
        super(name, username, password);
    }

    public int getAgencyID() { return agencyID; }

    public void setAgencyID(int agencyID) { this.agencyID = agencyID; }
}
