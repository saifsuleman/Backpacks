package io.pulsarlabs.backpackplugin.models;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackpackHolder implements InventoryHolder {
    private final Backpack backpack;
    private ItemStack backpackItem;
    private Inventory inventory;

    public BackpackHolder(Backpack backpack, ItemStack backpackItem) {
        this.backpack = backpack;
        this.backpackItem = backpackItem;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public ItemStack getBackpackItem() {
        return backpackItem;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setBackpackItem(ItemStack backpackItem) {
        this.backpackItem = backpackItem;
    }
}
