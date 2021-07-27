package pl.pijok.dailyoffers.buyer;

import pl.pijok.dailyoffers.offers.Offer;

import java.util.HashMap;

public class Buyer {

    private HashMap<Offer, Integer> usedOffers;

    public Buyer(HashMap<Offer, Integer> usedOffers){
        this.usedOffers = usedOffers;
    }

    public HashMap<Offer, Integer> getUsedOffers() {
        return usedOffers;
    }

    public void setUsedOffers(HashMap<Offer, Integer> usedOffers) {
        this.usedOffers = usedOffers;
    }

    public void clearUsedOffers(){
        usedOffers.clear();
    }
}
