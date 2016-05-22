package datalayer;

public class AgencyGateway {
    public String getAgencyByUserID(int userID){
        if(userID < 1){
            return "";
        }

        return DataGateway.request(
                "select * from agencies join users on users.id = agencies.user_id and users.id = " + userID);
    }

    public String getAllAgencys(){
        return DataGateway.request("select * from agencies join users on users.id = agencies.user_id");
    }

    public int getAgencyIdByUserId(int userID){
        if(userID < 1){
            return -1;
        }

        String tmp = DataGateway.request("select id from agencies where user_id = " + userID);

        if(tmp.isEmpty()){
            return -2;
        }

        tmp = tmp.substring(0, tmp.length() - 1);

        int result = Integer.parseInt(tmp);

        return result;
    }

    public void addAgency(int userID){
        if(userID < 1){
            return;
        }

        DataGateway.update("insert into agencies (user_id) values ("
                + userID + ")");
    }
}
