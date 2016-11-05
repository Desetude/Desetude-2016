package org.devathon.contest2016.machines;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;
import org.devathon.contest2016.utils.ItemStackBuilder;
import org.devathon.contest2016.utils.NBTUtils;

import java.util.Optional;

public class SmelterMachine extends SimpleMachine {

    public SmelterMachine() {
        super("Smelter", ChatColor.GOLD + "Compressor");

        this.addProcess(Process.create(
                item -> {
                    Optional<String> optDust = NBTUtils.getString(item, "dust");
                    return optDust.isPresent() && optDust.get().equals("iron") ? 1 : 0;
                },
                new ItemStackBuilder()
                        .material(Material.IRON_INGOT)
                        .build(),
                450));
    }

}
