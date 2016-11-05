package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.cmds.TestCommand;
import org.devathon.contest2016.listeners.MachineListener;
import org.devathon.contest2016.machines.MaceratorMachine;
import org.devathon.contest2016.machines.MachineFactory;
import org.devathon.contest2016.machines.SmelterMachine;
import org.devathon.contest2016.menu.MenuListener;

public class DevathonPlugin extends JavaPlugin {

    public static DevathonPlugin get() {
        return JavaPlugin.getPlugin(DevathonPlugin.class);
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new MachineListener(), this);

        this.getCommand("test").setExecutor(new TestCommand());

        MachineFactory.registerMachine("macerator", MaceratorMachine::new);
        MachineFactory.registerMachine("smelter", SmelterMachine::new);
    }

    @Override
    public void onDisable() {
    }

}

