package io.pulsarlabs.backpackplugin.models;

import io.pulsarlabs.backpackplugin.item.DataContainerController;
import io.pulsarlabs.backpackplugin.item.ItemStackBuilder;
import io.pulsarlabs.backpackplugin.serializer.InventorySerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.UUID;

public class Backpack {
    private final String id;
    private final ItemStack item;
    private final int rows;

    public Backpack(String id, ItemStack item, int rows) {
        this.id = id;
        this.rows = rows;
        this.item = ItemStackBuilder.of(item)
                .data()
                .set("BACKPACK_ID", PersistentDataType.STRING, id)
                .item()
                .build();
    }

    public ItemStack getItem() {
        return ItemStackBuilder.of(item.clone())
                .data()
                .set("BACKPACK_UUID", PersistentDataType.STRING, UUID.randomUUID().toString())
                .item().build();
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

        DataContainerController data = ItemStackBuilder.of(itemStack).data();
        String id = data.get("BACKPACK_ID", PersistentDataType.STRING);

        return this.id.equals(id);
    }

    public void handleInteract(Player player, ItemStack itemStack) {
        String serialized = ItemStackBuilder.of(itemStack).data().get("BACKPACK_DATA", PersistentDataType.STRING);
        BackpackHolder holder = new BackpackHolder(this, itemStack);
        Inventory inventory = Bukkit.createInventory(holder, this.rows * 9, itemStack.displayName());
        holder.setInventory(inventory);
        ItemStack[] contents = null;

        if (serialized != null) {
            try {
                contents = InventorySerializer.itemStackArrayFromBase64(serialized);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (contents != null) {
            for (int i = 0; i < contents.length; i++) {
                ItemStack contentItem = contents[i];
                inventory.setItem(i, contentItem);
            }
        }

        player.openInventory(inventory);
    }

    public void save(Inventory inventory) {
        if (!(inventory.getHolder() instanceof BackpackHolder)) {
            return;
        }

        ItemStack backpackItem = ((BackpackHolder) inventory.getHolder()).getBackpackItem();
        ItemStackBuilder.of(backpackItem).data()
                .set("BACKPACK_DATA", PersistentDataType.STRING, InventorySerializer.serializeInventory(inventory));
    }

    public static boolean equals(ItemStack i1, ItemStack i2) {
        DataContainerController d1 = ItemStackBuilder.of(i1).data();
        DataContainerController d2 = ItemStackBuilder.of(i2).data();

        String u1 = d1.get("BACKPACK_UUID", PersistentDataType.STRING);
        String u2 = d2.get("BACKPACK_UUID", PersistentDataType.STRING);

        return u1 != null && u1.equals(u2);
    }
}
