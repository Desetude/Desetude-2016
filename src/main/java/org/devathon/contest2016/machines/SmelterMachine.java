package org.devathon.contest2016.machines;

import net.minecraft.server.v1_10_R1.RecipesFurnace;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;
import org.devathon.contest2016.utils.ItemStackBuilder;
import org.devathon.contest2016.utils.NBTUtils;
import org.devathon.contest2016.utils.NMSUtils;

import java.util.Map;
import java.util.Optional;

public class SmelterMachine extends SimpleMachine {

    public SmelterMachine(Location location, boolean advanced) {
        super((advanced ? "Advanced" : "Basic") + " Smelter", (advanced ? ChatColor.GOLD + "Advanced" : ChatColor.YELLOW + "Basic") + " Smelter",
                advanced ? 3 : 1, location);

        for (Map.Entry<net.minecraft.server.v1_10_R1.ItemStack, net.minecraft.server.v1_10_R1.ItemStack> recipe :
                RecipesFurnace.getInstance().getRecipes().entrySet()) {
            this.addProcesses(
                    Process.create(item -> item.getType().equals(NMSUtils.getCraftMirror(recipe.getKey()).getType()) ? 1 : 0,
                            NMSUtils.getCraftMirror(recipe.getValue()), advanced ? 100 : 200));
        }

        this.addProcesses(
                this.createDustProcess("iron", Material.IRON_INGOT, advanced, 450),
                this.createDustProcess("gold", Material.GOLD_INGOT, advanced, 670),
                this.createDustProcess("emerald", Material.EMERALD, advanced, 800),
                this.createDustProcess("diamond", Material.DIAMOND, advanced, 1000)
        );
    }

    private Process createDustProcess(String dust, Material output, boolean advanced, long processingPower) {
        return Process.create(item -> {
            Optional<String> optDust = NBTUtils.getString(item, "dust");
            return optDust.isPresent() && optDust.get().equals(dust) ? 1 : 0;
        }, new ItemStackBuilder()
                .material(output)
                .build(), advanced ? processingPower / 2 : processingPower);
    }

}
