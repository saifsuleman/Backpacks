package io.pulsarlabs.backpackplugin;

import io.pulsarlabs.backpackplugin.managers.BackpackManager;
import io.pulsarlabs.backpackplugin.managers.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BackpackPlugin extends JavaPlugin {
    private static BackpackPlugin instance;

    private MenuManager menuManager;
    private BackpackManager backpackManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.menuManager = new MenuManager();
        this.backpackManager = new BackpackManager(this);
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

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
