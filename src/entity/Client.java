package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Victor
 */
public class Client extends User {

    private int clientID;
    private String phoneNumber;
    //private List<Integer> orders = new ArrayList<Integer>();

    public Client() {
        super("", "", "");
    }

    public Client(String name, String phoneNumber, String username, String password) {
        super(name, username, password);
        this.phoneNumber = phoneNumber;
    }

    public int getClientID() { return clientID; }

    public void setClientID(int clientID) { this.clientID = clientID; }

    public String getPhoneNumber() { return this.phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}
