package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.cmds.TestCommand;
import org.devathon.contest2016.menu.MenuListener;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);

        this.getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
    }

    public static DevathonPlugin get() {
        return JavaPlugin.getPlugin(DevathonPlugin.class);
    }

}

