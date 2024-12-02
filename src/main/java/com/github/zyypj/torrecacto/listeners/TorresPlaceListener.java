package com.github.zyypj.torrecacto.listeners;

import com.github.zyypj.torrecacto.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TorresPlaceListener implements Listener {

    private final Main plugin;

    public TorresPlaceListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerPlaceTower(PlayerInteractEvent e) {
        if (e.getItem() == null || e.getItem().getType() == Material.AIR) return;

        if (e.getClickedBlock() == null) {
            e.getPlayer().sendMessage("§cVocê deve clicar em um bloco válido para iniciar a construção.");
            return;
        }

        ItemStack item = e.getItem();
        NBTItem nbtItem = new NBTItem(item);

        if (!nbtItem.hasKey("towerLayers")) return;

        int layers = nbtItem.getInteger("towerLayers");

        if (!plugin.getTorresBuildQueue().canPlace(e.getClickedBlock().getLocation(), layers)) {
            e.getPlayer().sendMessage(plugin.getConfigManager().getMessage("cant-put"));
            return;
        }

        plugin.getTorresBuildQueue().addTask(e.getClickedBlock().getLocation(), plugin.getConfigManager().getTowerByLayers(layers));
        e.getPlayer().sendMessage(plugin.getConfigManager().getMessage("tower-placed"));
        item.setAmount(item.getAmount() - 1);
    }
}
