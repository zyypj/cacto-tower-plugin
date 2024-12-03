package com.github.zyypj.torrecacto.command;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TorreModel;
import com.github.zyypj.torrecacto.utils.ItemBuilder;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TorresCommand implements CommandExecutor {

    private final Main plugin;

    public TorresCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("torres.give")
                && !sender.hasPermission("torres.reload")) {
            sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("§a/torres reload");
            sender.sendMessage("§a/torres give &7<jogador> <id> <quantidade>");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("torres.reload")) {
                sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
                return false;
            }

            plugin.getConfigManager().reloadConfig();
            sender.sendMessage("§aConfigurações recarregadas com sucesso!");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("torres.give")) {
                sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
                return false;
            }

            if (args.length != 4) {
                sender.sendMessage("§cUse: /torres give <jogador> <idDaTorre> <quantidade>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJogador não encontrado.");
                return false;
            }

            String towerId = args[2];
            TorreModel config = plugin.getConfigManager().getTowerConfig(towerId);
            if (config == null) {
                sender.sendMessage("§cTorre com ID " + towerId + " não encontrada.");
                return false;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cQuantidade inválida.");
                return false;
            }

            final ItemStack item = config.getItem().clone();
            item.setAmount(quantity);
            target.getInventory().addItem(item);

            sender.sendMessage(plugin.getConfigManager().getMessage("send-tower")
                    .replace("{AMOUNT}", String.valueOf(quantity))
                    .replace("{TORRE-NAME}", config.getItem().getItemMeta().getDisplayName())
                    .replace("{JOGADOR}", target.getName())
                    .replace("&", "§"));
            target.sendMessage(plugin.getConfigManager().getMessage("receive-tower")
                    .replace("{AMOUNT}", String.valueOf(quantity))
                    .replace("{TORRE-NAME}", config.getItem().getItemMeta().getDisplayName())
                    .replace("&", "§"));
            return true;
        }

        sender.sendMessage("§a/torres reload");
        sender.sendMessage("§a/torres give &7<jogador> <id> <quantidade>");
        return false;
    }
}