package org.devathon.contest2016.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.menu.menu.Menu;

public class MenuItemClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Menu menu;
    private final int index;
    private final ClickType clickType;

    public MenuItemClickEvent(Player player, Menu menu, int index, ClickType clickType, ItemStack currentItem) {
        this.player = player;
        this.menu = menu;
        this.index = index;
        this.clickType = clickType;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public int getIndex() {
        return this.index;
    }

    public ClickType getClickType() {
        return this.clickType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
