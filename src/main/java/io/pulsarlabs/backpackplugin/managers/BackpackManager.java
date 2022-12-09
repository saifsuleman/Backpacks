package io.pulsarlabs.backpackplugin.managers;

import io.pulsarlabs.backpackplugin.BackpackPlugin;
import io.pulsarlabs.backpackplugin.listeners.BackpackListener;
import io.pulsarlabs.backpackplugin.models.Backpack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class BackpackManager {
    private final BackpackPlugin plugin;
    private final List<Backpack> backpacks;

    public BackpackManager(BackpackPlugin plugin) {
        this.plugin = plugin;
        this.backpacks = new ArrayList<>();

        new BackpackListener(this);
    }

    public List<Backpack> getBackpacks() {
        return backpacks;
    }

    public BackpackPlugin getPlugin() {
        return plugin;
    }

    public Backpack registerBackpack(String id, ItemStack item, int rows, ShapedRecipe recipe) {
        Backpack backpack = new Backpack(id, item, rows);
        this.backpacks.add(backpack);
        Bukkit.addRecipe(recipe);
        return backpack;
    }
}
