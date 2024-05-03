package de.philippcmd.vanishplugin.commands;

import de.philippcmd.vanishplugin.VanishPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class SuperVanishCommand implements CommandExecutor {

    private final VanishPlugin instance;
    public SuperVanishCommand(VanishPlugin vanishPlugin){
        this.instance = vanishPlugin;
    }
    public Set<Player> superVanishedPlayers = new HashSet<>();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("supervanish")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;
            if (player.hasPermission("vanish.supervanish")) {
                // Toggle supervanish status
                if (!superVanishedPlayers.contains(player)) {
                    superVanishedPlayers.add(player);
                    player.sendMessage("You are now in supervanish.");
                    for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                        onlinePlayer.hidePlayer(instance, player);
                    }
                } else {
                    superVanishedPlayers.remove(player);
                    player.sendMessage("You are no longer in supervanish.");
                    for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                        onlinePlayer.showPlayer(instance, player);
                    }
                }
            } else {
                player.sendMessage("You don't have permission to use this command.");
            }
            return true;
        }
        return false;
    }
}
