package datalayer;

public class UserGateway {

    public String getUserById(int id) {
        if (id < 1) {
            return "";
        }

        return DataGateway.request("select * from users where id = " + id);
    }

    public int getUserIdByUsername(String username) {
        if (username.isEmpty()) {
            return -1; // wrong login
        }

        String tmp = DataGateway.request("select id from users where username = \'" + username + "\'");

        if(tmp.isEmpty()){
            return -1; // no such user
        }

        tmp = tmp.substring(0, tmp.length() - 1);

        return Integer.parseInt(tmp);
    }

    public int authUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return -1; // wrong login or password
        }

        String tmp = DataGateway.request("select id from users where username = \'" + username
                + "\' and password = \'" + password + "\'");

        if (!tmp.isEmpty()) {
            tmp = tmp.substring(0, tmp.length() - 1);
            int userid = Integer.parseInt(tmp);

            if (userid > 0) {
                return userid;
            } else {
                return -2; // no user in db
            }
        }else{
            return -2; // no user in db
        }
    }

    public int addUser(String name, String username, String password) {
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return -1; // wrong parameters
        }

        DataGateway.update("insert into users (name, username, password) values (\'"
                + name + "\', \'" + username + "\', \'" + password + "\')");

        return 0;
    }
}

