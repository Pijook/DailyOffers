package pl.pijok.dailyoffers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pijok.dailyoffers.DailyOffers;
import pl.pijok.dailyoffers.essentials.ChatUtils;

public class DailyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, "&cTa komenda jest tylko dla graczy!");
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0){
            DailyOffers.getOffersController().openGui(player);
            return true;
        }

        ChatUtils.sendMessage(player, "&7/" + label);
        return true;
    }
}
