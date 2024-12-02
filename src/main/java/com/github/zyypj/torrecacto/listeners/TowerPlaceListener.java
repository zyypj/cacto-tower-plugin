package com.github.zyypj.torrecacto.listeners;

import com.github.zyypj.torrecacto.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TowerPlaceListener implements Listener {

    private final Main plugin;

    public TowerPlaceListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerPlaceTower(PlayerInteractEvent e) {

        if (e.getItem() == null
                || e.getItem().getType() == Material.AIR) return;

        ItemStack item = e.getItem();
        NBTItem nbtItem = new NBTItem(item);

        if (!nbtItem.hasKey("towerLayers")) return;

        int layers = nbtItem.getInteger("towerLayers");

        if (!plugin.getTowerBuildQueue().haveSpace(e.getClickedBlock().getLocation(), layers)) {
            e.getPlayer().sendMessage("§cEspaço insuficiente para construir a torre.");
            return;
        }

        plugin.getTowerBuildQueue().addTask(e.getClickedBlock().getLocation(), plugin.getConfigManager().getTowerConfigByLayers(layers));
        e.getPlayer().sendMessage("§aTorre iniciada com sucesso!");
        item.setAmount(item.getAmount() - 1);
    }
}
