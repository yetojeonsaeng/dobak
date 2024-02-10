package sushi.yeto.dobak;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import sushi.yeto.dobak.cmd.GachaCommand;
import sushi.yeto.dobak.event.InvClick;

public final class Main extends JavaPlugin implements Listener {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new InvClick(), this);

        getLogger().info("Plugin Enabled.");

        getCommand("도박").setExecutor(new GachaCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Disabled.");
    }

    public static Main getInstance() {
        return instance;
    }
}