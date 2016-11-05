package org.devathon.contest2016.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {

    private Material material;
    private String name;
    private List<String> lore;
    private int data;
    private int amount;
    private ItemMeta itemMeta;

    public ItemStackBuilder() {
        this.amount = 1;
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.data = itemStack.getDurability();
        this.amount = itemStack.getAmount();
        this.itemMeta = itemStack.getItemMeta();

        this.lore = this.itemMeta.getLore();
        this.name = this.itemMeta.getDisplayName();
    }

    public ItemStackBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemStackBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemStackBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemStackBuilder lore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemStackBuilder data(int data) {
        this.data = data;
        return this;
    }

    public ItemStackBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStackBuilder itemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    public ItemStack build() {
        checkNotNull(this.material, "Can not build an item stack with no material set.");
        ItemStack itemStack = new ItemStack(this.material, this.amount, (short) this.data);

        ItemMeta itemMeta;
        if (this.itemMeta != null) {
            itemMeta = this.itemMeta;
        } else {
            itemMeta = itemStack.getItemMeta();
        }


        if (this.name != null) {
            itemMeta.setDisplayName(this.name);
        }
        if (this.lore != null) {
            itemMeta.setLore(this.lore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
