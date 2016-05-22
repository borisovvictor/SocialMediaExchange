package datalayer;

public class ClientGateway {
    public String getClientByUserID(int userID){
        if(userID < 1){
            return "";
        }

        return DataGateway.request(
                "select * from clients join users on users.id = clients.user_id and users.id = " + userID);
    }

    public String getAllClients(){
        return DataGateway.request("select * from clients join users on users.id = clients.user_id");
    }

    public int getClientIdByUserId(int userID){
        if(userID < 1){
            return -1;
        }

        String tmp = DataGateway.request("select id from clients where user_id = " + userID);

        if(tmp.isEmpty()){
            return -2;
        }

        tmp = tmp.substring(0, tmp.length() - 1);

        int result = Integer.parseInt(tmp);

        return result;
    }

    public void addClient(int userID, String phoneNumber){
        if(userID < 1){
            return;
        }

        DataGateway.update("insert into clients (user_id, phone_number) values ("
                + userID + ",\'" + phoneNumber + "\')");
    }
}
