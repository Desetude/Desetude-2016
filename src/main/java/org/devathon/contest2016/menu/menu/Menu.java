package org.devathon.contest2016.menu.menu;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.devathon.contest2016.menu.MenuHolder;
import org.devathon.contest2016.menu.MenuItemClickEvent;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menu.items.MenuItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class Menu {

    protected final Rows rows;
    protected final String title;
    protected final Map<Integer, MenuItem> menuItems;
    protected final Set<Integer> moveableSlots;

    protected Inventory inventory;

    public Menu(Rows rows, String title) {
        this.rows = rows;
        this.title = title;

        this.menuItems = new HashMap<>();
        this.moveableSlots = new HashSet<>();
    }

    public static Optional<Menu> getFromInventory(Inventory inventory) {
        if (!(inventory.getHolder() instanceof MenuHolder)) {
            return Optional.empty();
        }

        return Optional.of(((MenuHolder) inventory.getHolder()).getMenu());
    }

    public void onItemClick(MenuItemClickEvent event) {
        MenuItem menuItem = this.menuItems.get(event.getIndex());

        if (menuItem != null) {
            menuItem.onClick(event);
        }
    }

    public final void updateItem(int index) {
        this.updateItem(index, this.menuItems.get(index));
    }

    public final void updateItem(int index, MenuItem menuItem) {
        this.inventory.setItem(index, menuItem.getIcon().get());
    }

    public final void updateItems() {
        if (this.inventory != null) {
            for (Map.Entry<Integer, MenuItem> entry : this.menuItems.entrySet()) {
                this.updateItem(entry.getKey(), entry.getValue());
            }
        }
    }

    public void addMoveableSlot(int index) {
        Preconditions.checkState(!this.menuItems.containsKey(index), "You can not add a moveable slot where a menu item already is.");
        this.moveableSlots.add(index);
    }

    public void addMenuItem(int index, MenuItem menuItem) {
        Preconditions.checkState(!this.moveableSlots.contains(index), "You can not set a menu item to a moveable slot.");
        this.menuItems.put(index, menuItem);
    }

    public boolean isMoveableSlot(int index) {
        return this.moveableSlots.contains(index);
    }

    public void removeMenuItem(int index) {
        this.menuItems.remove(index);
    }

    public void createInventory() {
        int size = this.rows.getSize();
        this.inventory = Bukkit.createInventory(new MenuHolder(Bukkit.createInventory(null, size, this.title), this), size, this.title);

        this.updateItems();
    }

    public void open(Player player) {
        if (this.inventory == null) {
            this.createInventory();
        }

        player.openInventory(this.inventory);
    }

    public Optional<Inventory> getInventory() {
        return Optional.ofNullable(this.inventory);
    }

    public void onClose(Player player) {
    }

}
