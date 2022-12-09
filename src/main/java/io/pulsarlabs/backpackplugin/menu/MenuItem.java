package io.pulsarlabs.backpackplugin.menu;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {
    private final ItemStack itemStack;
    private final Consumer<ClickType> clickHandler;

    public MenuItem(ItemStack itemStack, Consumer<ClickType> clickHandler) {
        this.itemStack = itemStack;
        this.clickHandler = clickHandler;
    }

    public MenuItem(ItemStack itemStack) {
        this(itemStack, clickType -> {});
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<ClickType> getClickHandler() {
        return clickHandler;
    }
}
