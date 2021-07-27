package pl.pijok.dailyoffers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pijok.dailyoffers.DailyOffers;
import pl.pijok.dailyoffers.Settings;
import pl.pijok.dailyoffers.essentials.ChatUtils;

public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("dailyoffers.admin")){
                ChatUtils.sendMessage(sender, "&cNie masz dostepu do tej komendy!");
                return true;
            }
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("resetCooldowns")){
                DailyOffers.getBuyerController().clearBuyers();
                ChatUtils.sendMessage(sender, "&aReseted all cooldowns!");
                return true;
            }
            if(args[0].equalsIgnoreCase("reloadOffers")){
                ChatUtils.sendMessage(sender, "&cReloading offers...");
                DailyOffers.getOffersController().load();

                ChatUtils.sendMessage(sender, "&cGenerating new offers...");
                DailyOffers.getOffersController().generateOffers();

                ChatUtils.sendMessage(sender, "&aDone!");
                return true;
            }
            if(args[0].equalsIgnoreCase("reloadSettings")){
                ChatUtils.sendMessage(sender, "&cReloading settings");
                Settings.load();

                ChatUtils.sendMessage(sender, "&aDone!");
                return true;
            }
            if(args[0].equalsIgnoreCase("generateOffers")){
                ChatUtils.sendMessage(sender, "&cGenerating new offers...");
                DailyOffers.getOffersController().generateOffers();

                ChatUtils.sendMessage(sender, "&aDone!");
                return true;
            }
        }

        ChatUtils.sendMessage(sender, "&7/" + label + " resetCooldowns");
        ChatUtils.sendMessage(sender, "&7/" + label + " reloadOffers");
        ChatUtils.sendMessage(sender, "&7/" + label + " generateOffers");
        ChatUtils.sendMessage(sender, "&7/" + label + " reloadSettings");
        return true;
    }
}
