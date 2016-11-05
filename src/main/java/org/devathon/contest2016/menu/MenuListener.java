package org.devathon.contest2016.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.devathon.contest2016.menu.menu.Menu;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null || event.getCurrentItem() == null || event.getRawSlot() > inventory.getSize() ||
                inventory.getItem(event.getRawSlot()) == null) {
            return;
        }

        InventoryHolder holder = inventory.getHolder();
        if(!(holder instanceof MenuHolder)) {
            return;
        }

        event.setCancelled(true);
        Menu menu = ((MenuHolder) holder).getMenu();

        MenuItemClickEvent menuEvent = new MenuItemClickEvent((Player) event.getWhoClicked(), menu, event.getRawSlot(), event.getClick());
        Bukkit.getPluginManager().callEvent(menuEvent);
    }

}
