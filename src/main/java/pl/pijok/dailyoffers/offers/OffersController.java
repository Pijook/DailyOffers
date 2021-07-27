package pl.pijok.dailyoffers.offers;

import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.pijok.dailyoffers.DailyOffers;
import pl.pijok.dailyoffers.Settings;
import pl.pijok.dailyoffers.buyer.Buyer;
import pl.pijok.dailyoffers.essentials.ChatUtils;
import pl.pijok.dailyoffers.essentials.ConfigUtils;
import pl.pijok.dailyoffers.essentials.Debug;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class OffersController {

    private List<Offer> dailyOffers;
    private List<Offer> currentOffers;

    public void load(){

        dailyOffers = new ArrayList<>();

        YamlConfiguration configuration = ConfigUtils.load("offers.yml", DailyOffers.getInstance());

        for(String key : configuration.getConfigurationSection("offers").getKeys(false)){

            ItemStack itemStack = ConfigUtils.getItemstack(configuration, "offers." + key);
            double price = configuration.getDouble("offers." + key + ".price");

            dailyOffers.add(new Offer(key, itemStack, price));
        }

    }

    public void startTimer(){
        Debug.log("Refresh hours: " + Settings.refreshHours.toString());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date();
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                String a = hours + ":" + minutes;
                Debug.log("Current hour:" + a);
                if(Settings.refreshHours.contains(a)){
                    Debug.log("Matched hour!");
                    generateOffers();
                }
            }
        }, TimeUnit.SECONDS.toMillis(60), TimeUnit.SECONDS.toMillis(60));
    }

    public void generateOffers(){
        Debug.log("&aGenerating new offers");
        Random random = new Random();
        currentOffers = new ArrayList<>();

        for(int i = 0; i < Settings.offersPerDay; i++){

            Offer offer = dailyOffers.get(random.nextInt(dailyOffers.size()));
            while(currentOffers.contains(offer)){
                offer = dailyOffers.get(random.nextInt(dailyOffers.size()));
            }
            currentOffers.add(offer);
        }

        DailyOffers.getBuyerController().clearBuyers();
    }

    public Offer getOfferByID(String ID){
        for(Offer offer : dailyOffers){
            if(offer.getID().equalsIgnoreCase(ID)){
                return offer;
            }
        }
        return null;
    }

    public void openGui(Player player){

        Gui gui = new Gui(Settings.offersGuiSettings.getRows(), Settings.offersGuiSettings.getTitle());

        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });

        gui.getFiller().fill(ItemBuilder.from(Material.LIGHT_GRAY_STAINED_GLASS_PANE).asGuiItem());

        Buyer buyer = DailyOffers.getBuyerController().getBuyer(player.getName());

        int i = 0;

        for(Offer offer : currentOffers){

            String name = ChatUtils.fixColor("&e&l" + offer.getCost() + " &eTC");
            String lore = "";
            int amount = 0;
            boolean boughtOut = false;
            if(buyer.getUsedOffers().containsKey(offer)){
                amount = buyer.getUsedOffers().get(offer);
            }

            if(amount >= Settings.maxBuysPerDay){
                lore = ChatUtils.fixColor("&c" + amount + "&7/" + "&c" + Settings.maxBuysPerDay);
                boughtOut = true;
            }
            else{
                lore = ChatUtils.fixColor("&e" + amount + "&7/" + "&e" + Settings.maxBuysPerDay);
            }

            boolean finalBoughtOut = boughtOut;
            int finalAmount = amount;
            GuiItem item = ItemBuilder.from(new ItemStack(offer.getItemStack())).setName(name).setLore("", lore).asGuiItem(event -> {

                Player target = (Player) event.getWhoClicked();

                if(finalBoughtOut){
                    gui.close(target);
                    ChatUtils.sendMessage(target, "&cWykupiles juz ten przedmiot!");
                    return;
                }

                if(DailyOffers.getEcon().getBalance(target) < offer.getCost()){
                    ChatUtils.sendMessage(target, "&cNie stac cie na ten przedmiot!");
                    gui.close(target);
                    return;
                }

                DailyOffers.getEcon().withdrawPlayer(target, offer.getCost());
                target.getInventory().addItem(new ItemStack(offer.getItemStack()));
                ChatUtils.sendMessage(target, "&aZakupiono przedmiot!");
                buyer.getUsedOffers().put(offer, finalAmount + 1);
                gui.close(target);

            });

            gui.setItem(Settings.offersGuiSettings.getSlots().get(i), item);
            i++;
        }

        gui.open(player);
    }

}
