package org.devathon.contest2016.machines;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;
import org.devathon.contest2016.utils.ItemStackBuilder;

public class MaceratorMachine extends SimpleMachine {

    public MaceratorMachine(Location location, boolean advanced) {
        super((advanced ? "Advanced" : "Basic") + " Macerator", ChatColor.GOLD + (advanced ? "Advanced" : "Basic") + " Macerator",
                advanced ? 3 : 1, location);

        this.addProcesses(
                this.dustProcess(Material.COAL, 7, ChatColor.DARK_GRAY + "Macerated coal", "iron", advanced, 450),
                this.dustProcess(Material.IRON_ORE, 7, ChatColor.GRAY + "Iron dust", "iron", advanced, 450),
                this.dustProcess(Material.GOLD_ORE, 11, ChatColor.YELLOW + "Gold dust", "gold", advanced, 670),
                this.dustProcess(Material.EMERALD_ORE, 10, ChatColor.GREEN + "Emerald dust", "emerald", advanced, 800),
                this.dustProcess(Material.DIAMOND_ORE, 6, ChatColor.AQUA + "Diamond dust", "diamond", advanced, 1000),
                Process.create(Material.LAPIS_ORE, new ItemStackBuilder()
                        .material(Material.INK_SACK)
                        .data(4)
                        .amount(8)
                        .build(), 300)
        );
    }

    private Process dustProcess(Material ore, int dyeData, String name, String dustKey, boolean advanced, long processingPower) {
        return Process.create(ore,
                new ItemStackBuilder()
                        .material(Material.INK_SACK)
                        .data(dyeData)
                        .amount(2)
                        .name(name)
                        .stringTag("dust", dustKey)
                        .build(),
                advanced ? processingPower / 2 : processingPower);
    }

}
