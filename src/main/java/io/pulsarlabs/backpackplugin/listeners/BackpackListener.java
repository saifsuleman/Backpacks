package io.pulsarlabs.backpackplugin.listeners;

import io.pulsarlabs.backpackplugin.managers.BackpackManager;
import io.pulsarlabs.backpackplugin.models.Backpack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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
}
