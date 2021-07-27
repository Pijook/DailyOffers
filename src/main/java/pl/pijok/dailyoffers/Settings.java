package pl.pijok.dailyoffers;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.pijok.dailyoffers.essentials.ChatUtils;
import pl.pijok.dailyoffers.essentials.ConfigUtils;
import pl.pijok.dailyoffers.essentials.GuiSettings;

import java.util.List;

public class Settings {

    public static int offersPerDay;
    public static List<String> refreshHours;

    public static GuiSettings offersGuiSettings;

    public static int maxBuysPerDay;

    public static void load(){

        YamlConfiguration configuration = ConfigUtils.load("config.yml", DailyOffers.getInstance());

        offersPerDay = configuration.getInt("offers.offersPerDay");
        refreshHours = configuration.getStringList("offers.refreshHours");

        maxBuysPerDay = configuration.getInt("offers.maxBuysPerDay");

        offersGuiSettings = new GuiSettings();
        offersGuiSettings.setRows(configuration.getInt("gui.rows"));
        offersGuiSettings.setTitle(ChatUtils.fixColor(configuration.getString("gui.title")));
        offersGuiSettings.setSlots(configuration.getIntegerList("gui.slotsWithOffers"));

    }

}
