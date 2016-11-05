package org.devathon.contest2016.utils;

import net.minecraft.server.v1_10_R1.TileEntityFurnace;
import org.bukkit.inventory.ItemStack;

public class FuelUtils {

    private FuelUtils() {
    }

    public static boolean isFuel(ItemStack itemStack) {
        return TileEntityFurnace.isFuel(NMSUtils.getNMS(itemStack));
    }

    public static int getFuelTime(ItemStack itemStack) {
        return TileEntityFurnace.fuelTime(NMSUtils.getNMS(itemStack));
    }

}
