package org.devathon.contest2016.machines;

import net.minecraft.server.v1_10_R1.RecipesFurnace;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;
import org.devathon.contest2016.utils.ItemStackBuilder;
import org.devathon.contest2016.utils.NBTUtils;
import org.devathon.contest2016.utils.NMSUtils;

import java.util.Map;
import java.util.Optional;

public class SmelterMachine extends SimpleMachine {

    public SmelterMachine() {
        super("Smelter", ChatColor.GOLD + "Compressor");

        for(Map.Entry<net.minecraft.server.v1_10_R1.ItemStack, net.minecraft.server.v1_10_R1.ItemStack> recipe :
                RecipesFurnace.getInstance().getRecipes().entrySet()) {
            this.addProcesses(
                    Process.create(item -> item.getType().equals(NMSUtils.getCraftMirror(recipe.getKey()).getType()) ? 1 : 0,
                            NMSUtils.getCraftMirror(recipe.getValue()), 200));
        }

        this.addProcesses(
                this.createDustProcess("iron", Material.IRON_INGOT, 450),
                this.createDustProcess("gold", Material.GOLD_INGOT, 670),
                this.createDustProcess("diamond", Material.DIAMOND, 900)
        );
    }

    private Process createDustProcess(String dust, Material output, long processingPower) {
        return Process.create(item -> {
                    Optional<String> optDust = NBTUtils.getString(item, "dust");
                    return optDust.isPresent() && optDust.get().equals("iron") ? 1 : 0;
                }, new ItemStackBuilder()
                        .material(output)
                        .build(), processingPower);
    }

}
