package io.pulsarlabs.backpackplugin.listeners;

import io.pulsarlabs.backpackplugin.BackpackPlugin;
import io.pulsarlabs.backpackplugin.menu.MenuHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {
    public MenuListener() {
        BackpackPlugin.getInstance().getServer().getPluginManager()
                .registerEvents(this, BackpackPlugin.getInstance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (!(inventory instanceof MenuHolder)) {

        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

    }
}
