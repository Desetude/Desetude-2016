package org.devathon.contest2016.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {

    private ArmorStand armorStand;

    public Hologram(Location location, String text) {
        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        this.armorStand.setGravity(false);
        this.armorStand.setCanPickupItems(false);
        this.armorStand.setVisible(false);
        this.armorStand.setCustomName(text);
        this.armorStand.setCustomNameVisible(true);
    }

    public void setText(String text) {
        this.armorStand.setCustomName(text);
    }

    public void remove() {
        this.armorStand.remove();
    }

}
