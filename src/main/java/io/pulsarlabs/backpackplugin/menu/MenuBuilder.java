package io.pulsarlabs.backpackplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MenuBuilder {
    private final Map<Integer, MenuItem> items;
    private final int rows;

    private MenuBuilder(int rows) {
        this.items = new HashMap<>();
        this.rows = rows;
    }

    public static MenuBuilder of(int rows) {
        return new MenuBuilder(rows);
    }

    public MenuBuilder item(int slot, MenuItem menuItem) {
        this.items.put(slot, menuItem);
        return this;
    }

    public MenuBuilder item(int slot, ItemStack itemStack) {
        return item(slot, new MenuItem(itemStack));
    }

    public MenuBuilder item(int slot, ItemStack itemStack, Consumer<ClickType> clickHandler) {
        return item(slot, new MenuItem(itemStack, clickHandler));
    }

    public MenuBuilder open(Player player) {
        MenuHolder holder = new MenuHolder(this);
        Inventory inventory = Bukkit.createInventory(holder, this.rows);
        holder.setInventory(inventory);

        for (Map.Entry<Integer, MenuItem> entry : this.items.entrySet()) {
            int slot = entry.getKey();
            MenuItem menuItem = entry.getValue();

            inventory.setItem(slot, menuItem.getItemStack());
        }

        player.openInventory(inventory);
        return this;
    }
}
