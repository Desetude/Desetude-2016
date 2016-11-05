package org.devathon.contest2016.machines;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;
import org.devathon.contest2016.utils.ItemStackBuilder;

public class CompressorMachine extends SimpleMachine {

    public CompressorMachine() {
        super("Compressor");

        this.addProcess(Process.create(
                Material.IRON_ORE,
                new ItemStackBuilder()
                        .material(Material.IRON_INGOT)
                        .build(),
                200));
    }

}
