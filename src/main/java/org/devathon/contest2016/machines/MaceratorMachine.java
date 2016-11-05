package org.devathon.contest2016.machines;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;
import org.devathon.contest2016.utils.ItemStackBuilder;

public class MaceratorMachine extends SimpleMachine {

    public MaceratorMachine() {
        super("Macerator", ChatColor.GOLD + "Macerator");

        this.addProcesses(
                this.dustProcess(Material.IRON_ORE, 7, ChatColor.GRAY + "Iron dust", "iron", 450),
                this.dustProcess(Material.GOLD_ORE, 11, ChatColor.YELLOW + "Gold dust", "gold", 670),
                this.dustProcess(Material.DIAMOND_ORE, 6, ChatColor.AQUA + "Diamond dust", "diamond", 900),
                Process.create(Material.LAPIS_ORE, new ItemStackBuilder()
                        .material(Material.INK_SACK)
                        .data(4)
                        .build(), 300)
        );
    }

    private Process dustProcess(Material ore, int dyeData, String name, String dustKey, long processingPower) {
        return Process.create(ore,
                new ItemStackBuilder()
                        .material(Material.INK_SACK)
                        .data(dyeData)
                        .amount(2)
                        .name(name)
                        .stringTag("dust", dustKey)
                        .build(),
                processingPower);
    }

}
