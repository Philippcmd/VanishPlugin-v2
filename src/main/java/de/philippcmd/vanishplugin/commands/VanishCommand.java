package de.philippcmd.vanishplugin.commands;

import de.philippcmd.vanishplugin.VanishPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class VanishCommand implements CommandExecutor{

    private final VanishPlugin instance;
    public VanishCommand(VanishPlugin vanishPlugin){
        this.instance = vanishPlugin;
    }

    private Set<Player> vanishedPlayers = new HashSet<>();
    private Set<Player> superVanishedPlayers = new HashSet<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;
            if (player.hasPermission("vanish.use")) {
                // Toggle vanish status
                if (!vanishedPlayers.contains(player)) {
                    for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                        if (!superVanishedPlayers.contains(onlinePlayer)) {
                            onlinePlayer.hidePlayer(instance, player);
                        }
                    }
                    vanishedPlayers.add(player);
                    player.sendMessage("You are now vanished.");
                } else {
                    for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                        onlinePlayer.showPlayer(instance, player);
                    }
                    vanishedPlayers.remove(player);
                    player.sendMessage("You are no longer vanished.");
                }
            } else {
                player.sendMessage("You don't have permission to use this command.");
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("vanish-show")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;
            if (player.hasPermission("vanish.show")) {
                for (Player vanishedPlayer : vanishedPlayers) {
                    if (!superVanishedPlayers.contains(vanishedPlayer)) {
                        player.showPlayer(instance, vanishedPlayer);
                    }
                }
                for (Player superVanishedPlayer : superVanishedPlayers) {
                    player.hidePlayer(instance, superVanishedPlayer);
                }
                player.sendMessage("Players in vanish mode are now visible to you.");
            } else {
                player.sendMessage("You don't have permission to use this command.");
            }
            return true;
        }
        return false;
    }
}
