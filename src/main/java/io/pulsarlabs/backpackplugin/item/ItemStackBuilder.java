package io.pulsarlabs.backpackplugin.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemStackBuilder {
    private final ItemStack itemStack;

    private ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static ItemStackBuilder of(ItemStack itemStack) {
        return new ItemStackBuilder(itemStack);
    }

    public static ItemStackBuilder of(Material material, int amount) {
        ItemStack itemStack = new ItemStack(material, amount);
        return new ItemStackBuilder(itemStack);
    }

    public static ItemStackBuilder of(Material material) {
        ItemStack itemStack = new ItemStack(material, 1);
        return new ItemStackBuilder(itemStack);
    }

    public static ItemStackBuilder skull(OfflinePlayer player) {
        return ItemStackBuilder
                .of(Material.PLAYER_HEAD)
                .meta(meta -> {
                    if (!(meta instanceof SkullMeta) || player.getPlayer() == null) {
                        return;
                    }

                    ((SkullMeta) meta).setOwningPlayer(player);
                });
    }

    public static ItemStackBuilder skull(String texture) {
        return ItemStackBuilder.of(Material.PLAYER_HEAD)
                .meta(meta -> {
                    if (!(meta instanceof SkullMeta)) {
                        return;
                    }

                    PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
                    profile.getProperties().add(new ProfileProperty("textures", texture));
                    ((SkullMeta) meta).setPlayerProfile(profile);
                });
    }

    public ItemStackBuilder meta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            consumer.accept(meta);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    protected ItemStack getItemStack() {
        return this.itemStack;
    }

    public ItemStackBuilder name(Component component) {
        return meta(meta -> meta.displayName(component));
    }

    public ItemStackBuilder name(String string) {
        return meta(meta -> meta.displayName(MiniMessage.miniMessage().deserialize(string)));
    }

    public ItemStackBuilder lore(List<String> strings) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return loreComponents(strings.stream().map(miniMessage::deserialize).collect(Collectors.toList()));
    }

    public ItemStackBuilder loreComponents(List<Component> components) {
        return meta(meta -> meta.lore(components));
    }

    public DataContainerController data() {
        return new DataContainerController(this);
    }

    public ItemStack build() {
        return this.itemStack.clone();
    }

}
