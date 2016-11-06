package org.devathon.contest2016.machines;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;

public class CompressorMachine extends SimpleMachine {

    public CompressorMachine(Location location) {
        super("Compressor", ChatColor.GOLD + "Compressor", 1, location);

        this.addProcesses(Process.create(Material.CLAY_BALL, 4, Material.CLAY, 50));
        this.addProcesses(Process.create(Material.CLAY_BRICK, 4, Material.BRICK, 50));
        this.addProcesses(Process.create(Material.CLAY, 4, Material.HARD_CLAY, 150));

        this.addProcesses(Process.create(Material.MELON, 9, Material.MELON_BLOCK, 150));

        this.addProcesses(Process.create(Material.GLOWSTONE_DUST, 4, Material.GLOWSTONE, 50));
        this.addProcesses(Process.create(Material.SNOW_BALL, 4, Material.SNOW_BLOCK, 50));
        this.addProcesses(Process.create(Material.NETHERRACK, 4, Material.NETHER_BRICK, 100));
        this.addProcesses(Process.create(Material.SNOW_BLOCK, 1, Material.ICE, 200));
        this.addProcesses(Process.create(Material.ICE, 1, Material.PACKED_ICE, 400));
        this.addProcesses(Process.create(Material.SAND, 1, Material.SANDSTONE, 200));

        this.addProcesses(Process.create(Material.COAL, 9, Material.COAL_BLOCK, 50));
        this.addProcesses(Process.create(Material.IRON_INGOT, 9, Material.IRON_BLOCK, 50));
        this.addProcesses(Process.create(Material.DIAMOND, 9, Material.DIAMOND_BLOCK, 50));
        this.addProcesses(Process.create(Material.REDSTONE, 9, Material.REDSTONE, 50));
        this.addProcesses(Process.create(Material.INK_SACK, 4, 9, Material.LAPIS_BLOCK, 50));
        this.addProcesses(Process.create(Material.EMERALD, 9, Material.EMERALD_BLOCK, 50));
    }

}
