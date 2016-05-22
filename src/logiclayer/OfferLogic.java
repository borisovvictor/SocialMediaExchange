package logiclayer;

import datalayer.OfferGateway;
import entity.Offer;

import java.util.ArrayList;
import java.util.List;

public class OfferLogic {

    OfferGateway og = new OfferGateway();

    public Offer getOfferById(int offerID){
        if(offerID < 1){
            return null;
        }

        String offerString = og.getOfferById(offerID);
        if(offerString.isEmpty()){
            return null;
        }

        String[] fields = offerString.split("\n");

        Offer o = new Offer();
        o.setID(Integer.parseInt(fields[0]));
        o.setPerformerID(Integer.parseInt(fields[1]));
        o.setPrice(Double.parseDouble(fields[2]));
        o.setPeriod(Integer.parseInt(fields[3]));
        o.setDescription(fields[4]);
        o.setSocialMedia(fields[5]);

        return o;
    }

    public List<Offer> getOffersByAgencyId(int agency_id)
    {
        List<Offer> offers = new ArrayList<>();

        String offersString = og.getOffersByAgencyId(agency_id);

        if(offersString.isEmpty()){
            return offers;
        }

        String[] fields = offersString.split("\n");
        int columnCount = 6;
        int count = fields.length / columnCount;

        for (int idx =0; idx < count; idx++)
        {
            Offer o = new Offer();
            o.setID(Integer.parseInt(fields[0 + idx * columnCount]));
            o.setPerformerID(Integer.parseInt(fields[1 + idx * columnCount]));
            o.setPrice(Double.parseDouble(fields[2 + idx * columnCount]));
            o.setPeriod(Integer.parseInt(fields[3 + idx * columnCount]));
            o.setDescription(fields[4 + idx * columnCount]);
            o.setSocialMedia(fields[5 + idx * columnCount]);
            offers.add(o);
        }

        return offers;
    }

    public String getAllOffers(){
        return og.getAllOffers();
    }

    public int addOffer(int perfomer_id, double price, int period, String description, String socail_media){
        if(perfomer_id < 1){
            return -1;
        }

        return og.addOffer(perfomer_id, price, period, description, socail_media);
    }
}
