package pl.pijok.dailyoffers;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pijok.dailyoffers.buyer.BuyerController;
import pl.pijok.dailyoffers.commands.AdminCommand;
import pl.pijok.dailyoffers.commands.DailyCommand;
import pl.pijok.dailyoffers.essentials.ChatUtils;
import pl.pijok.dailyoffers.essentials.ConfigUtils;
import pl.pijok.dailyoffers.essentials.Debug;
import pl.pijok.dailyoffers.listeners.JoinListener;
import pl.pijok.dailyoffers.offers.OffersController;

public class DailyOffers extends JavaPlugin {

    private static DailyOffers instance;
    private static Economy econ;
    private static OffersController offersController;
    private static BuyerController buyerController;

    @Override
    public void onEnable() {

        instance = this;

        offersController = new OffersController();
        buyerController = new BuyerController();

        ChatUtils.setPrefix("&7[&e&lDaily Offer&7] &r");
        Debug.setPrefix("[DailyOffers] ");
        ConfigUtils.setPlugin(this);

        if (!setupEconomy() ) {
            Debug.log(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        loadStuff();

        getCommand("dailyoffers").setExecutor(new DailyCommand());
        getCommand("adailyoffers").setExecutor(new AdminCommand());

    }

    @Override
    public void onDisable() {

        saveStuff();

    }

    public void loadStuff(){

        Debug.log("Loading DailyOffers v1.0 by Pijok");

        Debug.log("Loading settings...");
        Settings.load();

        Debug.log("Loading offers...");
        offersController.load();

        Debug.log("Starting timer...");
        offersController.startTimer();

        Debug.log("Generating offers...");
        offersController.generateOffers();

        Debug.log("Loading saved buyers...");
        buyerController.loadBuyers();

    }

    public void saveStuff(){

        Debug.log("Saving DailyOffers v1.0 by Pijok");

        Debug.log("Saving buyers to temporary file");
        buyerController.saveBuyers();

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static DailyOffers getInstance() {
        return instance;
    }

    public static Economy getEcon(){
        return econ;
    }

    public static OffersController getOffersController() {
        return offersController;
    }

    public static BuyerController getBuyerController() {
        return buyerController;
    }

}
