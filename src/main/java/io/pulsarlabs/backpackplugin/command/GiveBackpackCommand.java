package io.pulsarlabs.backpackplugin.command;

import io.pulsarlabs.backpackplugin.BackpackPlugin;
import io.pulsarlabs.backpackplugin.managers.BackpackManager;
import io.pulsarlabs.backpackplugin.models.Backpack;
import io.pulsarlabs.backpackplugin.text.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiveBackpackCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        handleCommand(sender, args);
        return true;
    }

    public void handleCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("backpacks.give")) {
            MessageUtil.message(sender, "<red>You do not have permission to do that!");
            return;
        }

        if (args.length < 2) {
            MessageUtil.message(sender, "<red>/givebackpack <player> <backpack>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MessageUtil.message(sender, "<red>That player was not found!");
            return;
        }

        Backpack backpack = BackpackPlugin.getInstance().getBackpackManager().getBackpack(args[1]);
        if (backpack == null) {
            MessageUtil.message(sender, "<red>Invalid backpack!");
            return;
        }

        target.getInventory().addItem(backpack.getItem());
        MessageUtil.message(sender, "<green>You have given " + target.getName() + " a " + backpack.getId() + " Backpack!");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return null;
        }

        if (args.length == 2) {
            BackpackManager manager = BackpackPlugin.getInstance().getBackpackManager();
            return manager.getBackpacks().stream().map(Backpack::getId).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
