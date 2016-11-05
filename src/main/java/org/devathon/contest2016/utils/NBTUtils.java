package org.devathon.contest2016.utils;

import net.minecraft.server.v1_10_R1.ItemStack;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import java.util.Optional;

public class NBTUtils {

    private NBTUtils() {
    }

    public static NBTTagCompound getTag(ItemStack itemStack) {
        NBTTagCompound comp = itemStack.getTag();
        if (comp == null) {
            comp = new NBTTagCompound();
            itemStack.setTag(comp);
        }

        return comp;
    }

    public static Optional<String> getString(org.bukkit.inventory.ItemStack itemStack, String key) {
        NBTTagCompound comp = getTag(NMSUtils.getNMS(itemStack));

        return comp.hasKey(key) ? Optional.of(comp.getString(key)) : Optional.empty();
    }

}
