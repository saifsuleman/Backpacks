package io.pulsarlabs.backpackplugin.models;

import io.pulsarlabs.backpackplugin.item.ItemDataController;
import io.pulsarlabs.backpackplugin.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

public class Backpack {
    private final String id;
    private final ItemStack item;
    private final ShapedRecipe recipe;
    private final int rows;

    public Backpack(String id, ItemStack item, int rows, ShapedRecipe recipe) {
        this.id = id;
        this.rows = rows;
        this.item = ItemStackBuilder.of(item)
                .data()
                .set("BACKPACK_ID", PersistentDataType.STRING, id)
                .item()
                .build();
        this.recipe = recipe;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public int getRows() {
        return rows;
    }

    public String getId() {
        return id;
    }

    public boolean isItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }

        if (item.getType() != itemStack.getType()) {
            return false;
        }

        ItemDataController.DataContainerController data = ItemStackBuilder.of(itemStack).data();
        String id = data.get("BACKPACK_ID", PersistentDataType.STRING);

        return this.id.equals(id);
    }

    public void handleInteract(Player player, ItemStack itemStack) {

    }
}
