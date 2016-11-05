package org.devathon.contest2016.utils;

import net.minecraft.server.v1_10_R1.ItemStack;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;

public class NMSUtils {

    private NMSUtils() {
    }

    public static org.bukkit.inventory.ItemStack getCraftMirror(ItemStack itemStack) {
        return CraftItemStack.asCraftMirror(itemStack);
    }

    public static org.bukkit.inventory.ItemStack getCraftCopy(org.bukkit.inventory.ItemStack itemStack) {
        return CraftItemStack.asCraftCopy(itemStack);
    }

    public static ItemStack getNMS(org.bukkit.inventory.ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack);
    }

    public static org.bukkit.inventory.ItemStack asCraftItemStack(ItemStack itemStack) {
        return CraftItemStack.asCraftMirror(itemStack);
    }

}
