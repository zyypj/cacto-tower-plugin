package com.github.zyypj.torrecacto.listeners;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TorreModel;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
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
    public void playerPlaceTower(final PlayerInteractEvent e) {
        if (e.getItem() == null || e.getItem().getType() == Material.AIR) return;

        if (e.getClickedBlock() == null) {
            e.getPlayer().sendMessage("§cVocê deve clicar em um bloco válido para iniciar a construção.");
            return;
        }

        final ItemStack item = e.getItem();
        final NBTItem nbtItem = new NBTItem(item);

        if (!nbtItem.hasKey("torre-key")) return;

        final TorreModel torre = plugin.getConfigManager().getTowerConfig(nbtItem.getString("torre-key"));
        final Location baseLocation = e.getClickedBlock().getLocation().add(0, 1, 0);

        if (!plugin.getTorresBuildQueue().canPlace(e.getPlayer(), baseLocation, torre.getLayers())) {
            e.getPlayer().sendMessage(plugin.getConfigManager().getMessage("cant-put"));
            return;
        }

        plugin.getTorresBuildQueue().addTask(baseLocation, torre);
        e.getPlayer().sendMessage(plugin.getConfigManager().getMessage("tower-placed"));

        if(item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
            e.getPlayer().setItemInHand(item);
        } else e.getPlayer().setItemInHand(null);
    }
}
