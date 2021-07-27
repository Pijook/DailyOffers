package pl.pijok.dailyoffers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.pijok.dailyoffers.DailyOffers;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        DailyOffers.getBuyerController().createBuyer(event.getPlayer().getName());

    }
}
