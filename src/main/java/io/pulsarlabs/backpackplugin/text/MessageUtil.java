package io.pulsarlabs.backpackplugin.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {
    public static Component parse(String s) {
        return MiniMessage.miniMessage().deserialize(s);
    }

    public static void message(CommandSender player, String s) {
        player.sendMessage(parse(s));
    }
}
