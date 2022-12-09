package io.pulsarlabs.backpackplugin;

import io.pulsarlabs.backpackplugin.item.ItemStackBuilder;
import io.pulsarlabs.backpackplugin.managers.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class BackpackPlugin extends JavaPlugin {
    private static BackpackPlugin instance;

    private BackpackManager backpackManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.backpackManager = new BackpackManager(this);

        ItemStack smallItem = ItemStackBuilder.of(Material.ARROW)
                .name("<rainbow>Small Backpack")
                .build();
        NamespacedKey smallKey = new NamespacedKey(this, "SMALL_BACKPACK");
        ShapedRecipe smallRecipe = new ShapedRecipe(smallKey, smallItem);
        smallRecipe.shape(
                "LLL",
                "LCL",
                "LLL"
        );
        smallRecipe.setIngredient('L', Material.LEATHER);
        smallRecipe.setIngredient('C', Material.CHEST);

        this.backpackManager.registerBackpack(
                "small",
                smallItem,
                3,
                smallRecipe
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BackpackPlugin getInstance() {
        return instance;
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }
}
