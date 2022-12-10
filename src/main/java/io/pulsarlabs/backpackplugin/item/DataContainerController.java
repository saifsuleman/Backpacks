package io.pulsarlabs.backpackplugin.item;

import io.pulsarlabs.backpackplugin.BackpackPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class DataContainerController {
    private static final Map<String, NamespacedKey> KEYS = new HashMap<>();
    private final ItemStackBuilder builder;

    protected DataContainerController(ItemStackBuilder builder) {
        this.builder = builder;
    }

    public ItemStackBuilder item() {
        return builder;
    }

    public <T, Z> DataContainerController set(String key, PersistentDataType<T, Z> type, Z value) {
        NamespacedKey namespacedKey = KEYS.computeIfAbsent(key, (ignored) -> new NamespacedKey(BackpackPlugin.getInstance(), key));
        builder.meta(meta -> {
            meta.getPersistentDataContainer().set(namespacedKey, type, value);
        });
        return this;
    }

    public <T, Z> Z get(String key, PersistentDataType<T, Z> type) {
        NamespacedKey namespacedKey = KEYS.computeIfAbsent(key, (ignored) -> new NamespacedKey(BackpackPlugin.getInstance(), key));
        return builder.getItemStack().getItemMeta().getPersistentDataContainer().get(namespacedKey, type);
    }
}
