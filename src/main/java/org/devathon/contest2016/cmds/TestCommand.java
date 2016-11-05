package org.devathon.contest2016.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.machines.CompressorMachine;
import org.devathon.contest2016.machines.SmelterMachine;
import org.devathon.contest2016.utils.ItemStackBuilder;

public class TestCommand implements CommandExecutor {

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do this.");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Invalid machine.");
            return true;
        }

        Player player = (Player) sender;

        switch (args[0].toLowerCase()) {
            case "compressor":
                player.getInventory().addItem(new ItemStackBuilder()
                        .material(Material.FURNACE)
                        .name(ChatColor.GOLD + "Compressor")
                        .stringTag("machine", "compressor")
                        .build());
                break;
            case "smelter":
                player.getInventory().addItem(new ItemStackBuilder()
                        .material(Material.FURNACE)
                        .name(ChatColor.GOLD + "Smelter")
                        .stringTag("machine", "smelter")
                        .build());
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid machine.");
                break;
        }

        return true;
    }

}
