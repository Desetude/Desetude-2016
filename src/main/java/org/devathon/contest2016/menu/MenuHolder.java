package org.devathon.contest2016.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.devathon.contest2016.menu.menu.Menu;

public class MenuHolder implements InventoryHolder {

    private final Inventory inventory;
    private final Menu menu;

    public MenuHolder(Inventory inventory, Menu menu) {
        this.inventory = inventory;
        this.menu = menu;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public Menu getMenu() {
        return this.menu;
    }

}
