package org.devathon.contest2016.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.menu.MenuItemClickEvent;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menu.items.MenuItem;
import org.devathon.contest2016.menu.menu.Menu;
import org.devathon.contest2016.utils.ItemStackBuilder;

public class SimpleMachineMenu extends Menu {

    public SimpleMachineMenu(Player player, Rows rows, String title) {
        super(player, rows, title);

        this.addMoveableSlot(11);
        this.addMenuItems();
    }

    public void addMenuItems() {
        for (int i : new int[]{1, 2, 3, 10, 12, 19, 20, 21}) {
            this.addMenuItem(i, MenuItem.create(new ItemStackBuilder().material(Material.STAINED_GLASS_PANE)
                            .data(14)
                            .name(ChatColor.YELLOW + "Input")
                            .build()));
        }

        for (int i : new int[]{5, 6, 7, 14, 16, 23, 24, 25}) {
            this.addMenuItem(i, MenuItem.create(new ItemStackBuilder().material(Material.STAINED_GLASS_PANE)
                    .data(5)
                    .name(ChatColor.YELLOW + "Output")
                    .build()));
        }

        for (int i : new int[]{39, 40, 41, 48, 50}) {
            this.addMenuItem(i, MenuItem.create(new ItemStackBuilder().material(Material.STAINED_GLASS_PANE)
                    .data(7)
                    .name(ChatColor.YELLOW + "Fuel")
                    .build()));
        }
    }

    @Override public void onItemClick(MenuItemClickEvent event) {
        super.onItemClick(event);
    }

    @Override
    public void open() {
        super.open();
    }

}
