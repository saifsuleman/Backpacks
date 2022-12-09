package io.pulsarlabs.backpackplugin.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class MenuHolder implements InventoryHolder {
    private Inventory inventory;
    private final MenuBuilder builder;

    public MenuHolder(MenuBuilder builder) {
        this.builder = builder;
    }

    public MenuBuilder getBuilder() {
        return builder;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
