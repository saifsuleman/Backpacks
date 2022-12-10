package io.pulsarlabs.backpackplugin.listeners;

import io.pulsarlabs.backpackplugin.BackpackPlugin;
import io.pulsarlabs.backpackplugin.managers.BackpackManager;
import io.pulsarlabs.backpackplugin.models.Backpack;
import io.pulsarlabs.backpackplugin.models.BackpackHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
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

    public void saveBackpack(Player player, Inventory inventory) {
        InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof BackpackHolder backpackHolder)) {
            return;
        }

        Backpack backpack = backpackHolder.getBackpack();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }
            if (Backpack.equals(backpackHolder.getBackpackItem(), itemStack)) {
                inventory.setItem(i, null);
                backpackHolder.setBackpackItem(itemStack);

                backpack.save(inventory);

                if (player.isOnline()) {
                    player.getInventory().addItem(itemStack).values().forEach(value -> {
                        player.getLocation().getWorld().dropItem(player.getLocation(), value);
                    });
                }

                return;
            }
        }

        backpack.save(inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        saveBackpack((Player) event.getPlayer(), event.getInventory());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        saveBackpack(player, inventory);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        saveBackpack(player, inventory);
    }
}
