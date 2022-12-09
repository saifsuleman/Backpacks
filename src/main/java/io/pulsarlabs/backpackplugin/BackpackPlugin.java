package io.pulsarlabs.backpackplugin;

import io.pulsarlabs.backpackplugin.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class BackpackPlugin extends JavaPlugin {
    private static BackpackPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BackpackPlugin getInstance() {
        return instance;
    }
}
