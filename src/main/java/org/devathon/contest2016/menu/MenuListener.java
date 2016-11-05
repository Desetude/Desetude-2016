package org.devathon.contest2016.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.devathon.contest2016.menu.menu.Menu;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null || event.getSlot() > inventory.getSize()) {
            return;
        }

        InventoryHolder holder = inventory.getHolder();
        if (!(holder instanceof MenuHolder)) {
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && //Put into menu from player inventory
                    event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof MenuHolder) {
                event.setCancelled(true);
            }

            return;
        }

        Menu menu = ((MenuHolder) holder).getMenu();

        if (menu.isMoveableSlot(event.getSlot())) {
            return;
        }

        MenuItemClickEvent menuEvent = new MenuItemClickEvent((Player) event.getWhoClicked(), menu, event.getSlot(), event.getClick(),
                event.getCurrentItem());
        Bukkit.getPluginManager().callEvent(menuEvent);

        menu.onItemClick(menuEvent);

        event.setCancelled(true);
    }

    @EventHandler public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory == null) {
            return;
        }

        InventoryHolder holder = inventory.getHolder();
        if (!(holder instanceof MenuHolder)) {
            return;
        }

        Menu menu = ((MenuHolder) holder).getMenu();
        for (int slot : event.getRawSlots()) {
            if (!menu.isMoveableSlot(slot)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
