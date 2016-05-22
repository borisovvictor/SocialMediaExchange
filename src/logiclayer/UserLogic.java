package logiclayer;

import datalayer.UserGateway;

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
}
