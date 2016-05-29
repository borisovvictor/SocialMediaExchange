package logiclayer;

import datalayer.UserGateway;
import servicelayer.Util;

public class UserLogic {
    UserGateway ug = new UserGateway();

    public int authUser(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return -1;
        }

        return ug.authUser(username, password);
    }

    public int addUser(String name, String username, String password){
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return -1; // wrong parameters
        }

        ug.addUser(name, username, password);

        return 0;
    }

    public int getUserIdByUsername(String username){
        return ug.getUserIdByUsername(username);
    }

    public void logIn(int userId)
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
}
