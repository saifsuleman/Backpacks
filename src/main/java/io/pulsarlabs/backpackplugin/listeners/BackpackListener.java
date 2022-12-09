package io.pulsarlabs.backpackplugin.listeners;

import io.pulsarlabs.backpackplugin.managers.BackpackManager;
import io.pulsarlabs.backpackplugin.models.Backpack;
import io.pulsarlabs.backpackplugin.models.BackpackHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BackpackListener implements Listener {
    private final BackpackManager manager;

    public BackpackListener(BackpackManager manager) {
        this.manager = manager;
        this.manager.getPlugin().getServer().getPluginManager().registerEvents(this, manager.getPlugin());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        List<Backpack> backpacks = manager.getBackpacks();

        for (Backpack backpack : backpacks) {
            if (!backpack.isItem(itemStack)) {
                continue;
            }

            backpack.handleInteract(event.getPlayer(), itemStack);
            break;
        }
    }

    public void saveBackpack(Inventory inventory) {
        InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof BackpackHolder)) {
            return;
        }

        BackpackHolder backpackHolder = (BackpackHolder) holder;
        Backpack backpack = backpackHolder.getBackpack();
        backpack.save(inventory);
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent event) {
        saveBackpack(event.getInventory());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        saveBackpack(event.getInventory());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        saveBackpack(inventory);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        saveBackpack(inventory);
    }
}
