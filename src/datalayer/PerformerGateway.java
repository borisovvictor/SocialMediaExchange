package datalayer;

public class PerformerGateway {
    public String getPerformerByUserID(int userID){
        if(userID < 1){
            return "";
        }

        return DataGateway.request(
                "select * from performers join users on users.id = performers.user_id and users.id = " + userID);
    }

    public String getAllPerformers(){
        return DataGateway.request("select * from performers join users on users.id = performers.user_id");
    }

    public int getPerformerIdByUserId(int userID){
        if(userID < 1){
            return -1;
        }

        String tmp = DataGateway.request("select id from performers where user_id = " + userID);

        if(tmp.isEmpty()){
            return -2;
        }

        tmp = tmp.substring(0, tmp.length() - 1);

        int result = Integer.parseInt(tmp);

        return result;
    }

    public void addPerformer(int userID, String phoneNumber, int agencyID){
        if(userID < 1){
            return;
        }

        DataGateway.update("insert into performers (user_id, phone_number, agency_id, money_amount) values ("
                + userID + ",\'" + phoneNumber + "\'," + agencyID + "," + 0 + ")");
    }

    public double getPerformerMoneyAmount(int performerID){
        if(performerID < 1){
            return -1;
        }
        String tmp = DataGateway.request("select money_amount from performers where id = " + performerID);
        if(tmp.isEmpty()){
            return -2;
        }
        tmp = tmp.substring(0, tmp.length() - 1);
        double result = Double.parseDouble(tmp);
        return result;
    }

    public void setPerformerMoneyAmount(int performerID, double moneyAmount){
        if(performerID < 1){
            return;
        }
        DataGateway.update("update performers set money_amount=" + moneyAmount + "where id=" + performerID);
    }

    public void addPerformerMoneyAmount(int performerID, double moneyAmount){
        if(performerID < 1){
            return;
        }
        DataGateway.update("update performers set money_amount=money_amount+" + moneyAmount + " where id=" + performerID);
    }

}
