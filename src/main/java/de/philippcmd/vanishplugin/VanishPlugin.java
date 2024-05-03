package de.philippcmd.vanishplugin;

import de.philippcmd.vanishplugin.commands.SuperVanishCommand;
import de.philippcmd.vanishplugin.commands.VanishCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class VanishPlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getLogger().info("VanishPlugin has been enabled!");
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("vanish-show").setExecutor(new VanishCommand(this));
        getCommand("supervanish").setExecutor(new SuperVanishCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("VanishPlugin has been disabled!");

    }
}