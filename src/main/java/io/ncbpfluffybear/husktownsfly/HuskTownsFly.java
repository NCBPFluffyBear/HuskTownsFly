package io.ncbpfluffybear.husktownsfly;

import me.william278.husktowns.HuskTownsAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class HuskTownsFly extends JavaPlugin {

    private static Plugin instance;
    private static HuskTownsAPI api;

    @Override
    public void onEnable() {
        instance = this;
        api = HuskTownsAPI.getInstance();
        // Register Event
        this.getServer().getPluginManager().registerEvents(new FlyListener(), this);
        // Register Task, 5 second check
        this.getServer().getScheduler().runTaskTimer(this, new CheckFlyTask(), 0, 100);
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static HuskTownsAPI getApi() {
        return api;
    }

}
