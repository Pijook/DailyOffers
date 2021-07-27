package pl.pijok.dailyoffers.buyer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;
import pl.pijok.dailyoffers.DailyOffers;
import pl.pijok.dailyoffers.essentials.ConfigUtils;
import pl.pijok.dailyoffers.offers.Offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuyerController {

    private HashMap<String, Buyer> buyers = new HashMap<>();

    public void createBuyer(String nickname){
        if(!buyers.containsKey(nickname)){
            buyers.put(nickname, new Buyer(new HashMap<>()));
        }
    }

    public void clearBuyers(){
        List<String> toRemove = new ArrayList<>();
        for(String nickname : buyers.keySet()){
            Player player = Bukkit.getPlayer(nickname);

            if(player == null || !player.isOnline()){
                toRemove.add(nickname);
            }

            buyers.get(nickname).clearUsedOffers();
        }

        for(String a : toRemove){
            buyers.remove(a);
        }
    }

    public void saveBuyers(){
        YamlConfiguration configuration = ConfigUtils.load("temp.yml", DailyOffers.getInstance());

        configuration.set("temp", null);

        for(String nickname : buyers.keySet()){
            Buyer buyer = buyers.get(nickname);
            if(buyer.getUsedOffers().size() == 0){
                continue;
            }
            configuration.set("temp." + nickname, buyerToString(buyer));
        }

        ConfigUtils.save(configuration, "temp.yml");
    }

    public void loadBuyers(){
        YamlConfiguration configuration = ConfigUtils.load("temp.yml", DailyOffers.getInstance());

        if(!configuration.contains("temp")){
            return;
        }

        for(String nickname : configuration.getConfigurationSection("temp").getKeys(false)){

            buyers.put(nickname, stringToBuyer(configuration.getString("temp." + nickname)));

        }
    }

    public Buyer getBuyer(String nickname){
        return buyers.get(nickname);
    }

    private String buyerToString(Buyer buyer){
        StringBuilder a = new StringBuilder();
        int i = 0;
        for(Offer offer : buyer.getUsedOffers().keySet()){
            if(i != 0){
                a.append(";");
            }
            a.append(offer.getID()).append(":").append(buyer.getUsedOffers().get(offer));
            i++;
        }
        return a.toString();
    }

    private Buyer stringToBuyer(String a){
        String[] elements = a.split(";");
        Buyer buyer = new Buyer(new HashMap<>());

        for(String element : elements){
            String[] parts = element.split(":");
            buyer.getUsedOffers().put(DailyOffers.getOffersController().getOfferByID(parts[0]), Integer.parseInt(parts[1]));
        }

        return buyer;
    }

}
