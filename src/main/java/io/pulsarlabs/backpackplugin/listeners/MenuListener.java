package io.pulsarlabs.backpackplugin.listeners;

import io.pulsarlabs.backpackplugin.BackpackPlugin;
import io.pulsarlabs.backpackplugin.menu.MenuBuilder;
import io.pulsarlabs.backpackplugin.menu.MenuHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
    public MenuListener() {
        BackpackPlugin.getInstance().getServer().getPluginManager()
                .registerEvents(this, BackpackPlugin.getInstance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            return;
        }
        if (!(inventory.getHolder() instanceof MenuHolder)) {
            return;
        }
        MenuHolder holder = (MenuHolder) inventory.getHolder();
        MenuBuilder builder = holder.getBuilder();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

    }
}
