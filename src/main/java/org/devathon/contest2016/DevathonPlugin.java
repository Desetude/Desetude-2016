package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.cmds.TestCommand;
import org.devathon.contest2016.listeners.MachineListener;
import org.devathon.contest2016.machines.CompressorMachine;
import org.devathon.contest2016.machines.MaceratorMachine;
import org.devathon.contest2016.machines.MachineFactory;
import org.devathon.contest2016.machines.SmelterMachine;
import org.devathon.contest2016.menu.MenuListener;
import org.devathon.contest2016.utils.ItemStackBuilder;

public class DevathonPlugin extends JavaPlugin {

    public static DevathonPlugin get() {
        return JavaPlugin.getPlugin(DevathonPlugin.class);
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new MachineListener(), this);

        this.getCommand("test").setExecutor(new TestCommand());

        MachineFactory.registerMachine("macerator", location -> new MaceratorMachine(location, false));
        MachineFactory.registerMachine("advanced_macerator", location -> new MaceratorMachine(location, true));
        MachineFactory.registerMachine("smelter",  location -> new SmelterMachine(location, false));
        MachineFactory.registerMachine("advanced_smelter",  location -> new SmelterMachine(location, true));
        MachineFactory.registerMachine("compressor", CompressorMachine::new);

        this.addRecipes();
    }

    private void addRecipes() {
        this.getServer().addRecipe(new ShapedRecipe(
                new ItemStackBuilder()
                        .material(Material.FURNACE)
                        .name(ChatColor.GOLD + "Smelter")
                        .stringTag("machine", "smelter")
                        .build())
                .shape("cfc", "cpc", "cfc")
                .setIngredient('c', Material.COBBLESTONE)
                .setIngredient('f', Material.FURNACE)
                .setIngredient('p', Material.PISTON_BASE));

        this.getServer().addRecipe(new ShapedRecipe(
                new ItemStackBuilder()
                        .material(Material.FURNACE)
                        .name(ChatColor.GOLD + "Smelter")
                        .stringTag("machine", "smelter")
                        .build())
                .shape("ifi", "ipi", "ifi")
                .setIngredient('i', Material.IRON_BLOCK)
                .setIngredient('f', Material.FURNACE)
                .setIngredient('p', Material.PISTON_BASE));

        this.getServer().addRecipe(new ShapedRecipe(
                new ItemStackBuilder()
                        .material(Material.FURNACE)
                        .name(ChatColor.GOLD + "Macerator")
                        .stringTag("machine", "macerator")
                        .build())
                .shape("cdc", "cpc", "cdc")
                .setIngredient('c', Material.COBBLESTONE)
                .setIngredient('d', Material.DISPENSER)
                .setIngredient('p', Material.PISTON_BASE));

        this.getServer().addRecipe(new ShapedRecipe(
                new ItemStackBuilder()
                        .material(Material.FURNACE)
                        .name(ChatColor.GOLD + "Advanced Macerator")
                        .stringTag("machine", "advanced_macerator")
                        .build())
                .shape("idi", "ipi", "idi")
                .setIngredient('i', Material.IRON_BLOCK)
                .setIngredient('d', Material.DISPENSER)
                .setIngredient('p', Material.PISTON_BASE));
    }

    @Override
    public void onDisable() {
        MachineFactory.getMachines().forEach(entry -> entry.getValue().onBreak(entry.getKey()));
    }

}

