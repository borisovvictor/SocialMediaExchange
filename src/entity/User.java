package entity;

/**
 * Created by Victor
 */
public class User {

    protected int id;
    protected String name;
    protected String username;
    protected String password;

    public User(String name, String username, String password)
    {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getID() { return id; }

    public void setID(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}