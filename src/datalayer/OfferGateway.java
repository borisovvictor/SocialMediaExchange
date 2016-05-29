package datalayer;

/**
 * Created by Victor
 */
public class OfferGateway {

    public int addOffer(int performer_id, double price, int period,
                         String description, String socialMedia)
    {
        String tmp = DataGateway.request("insert into offers (performer_id, price, period, description, social_media) values ("
                + performer_id + "," + price + "," + period
                + ",\'" + description + "\',\'" + socialMedia + "\') returning id");

        return Integer.parseInt(tmp.split("\n")[0]);
    }

    public void removeOffer(int offer_id)
    {
        DataGateway.update("delete from offers where id=" + offer_id);
    }

    public String getOfferById(int offerid){
        if(offerid < 1){
            return "";
        }

        return DataGateway.request(
                "select * from offers where offers.id = " + offerid);
    }

    public String getOffersByAgencyId(int agencyid){
        if(agencyid < 1){
            return "";
        }

        return DataGateway.request(
                "select offers.id,offers.performer_id,offers.price,offers.period,offers.description,offers.social_media "
                        + "from (offers join performers on offers.performer_id = performers.id and performers.agency_id = " + agencyid + ")");
    }

    public String getOffersByPerformerId(int performerid){
        if(performerid < 1){
            return "";
        }

        return DataGateway.request(
                "select * from offers where offers.performer_id = " + performerid);
    }

    public String getAllOffers(){
        return DataGateway.request("select * from offers");
    }
}
