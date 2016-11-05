package org.devathon.contest2016.menu.items;

import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.menu.MenuItemClickEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MenuItem {

    protected final Supplier<ItemStack> icon;
    protected final Consumer<MenuItemClickEvent> action;

    private MenuItem(Supplier<ItemStack> icon, Consumer<MenuItemClickEvent> action) {
        this.icon = icon;
        this.action = action;
    }

    public static MenuItem create(Supplier<ItemStack> icon, Consumer<MenuItemClickEvent> action) {
        return new MenuItem(icon, action);
    }

    public static MenuItem create(ItemStack icon, Consumer<MenuItemClickEvent> action) {
        return create(() -> icon, action);
    }

    public static MenuItem create(Supplier<ItemStack> icon, Runnable action) {
        return create(icon, event -> action.run());
    }

    public static MenuItem create(ItemStack icon, Runnable action) {
        return create(() -> icon, action);
    }

    public static MenuItem create(Supplier<ItemStack> icon) {
        return create(icon, () -> {});
    }

    public static MenuItem create(ItemStack icon) {
        return create(() -> icon, () -> {});
    }

    public void onClick(MenuItemClickEvent event) {
        this.action.accept(event);
    }

    public Supplier<ItemStack> getIcon() {
        return this.icon;
    }

    public Consumer<MenuItemClickEvent> getAction() {
        return this.action;
    }

}
