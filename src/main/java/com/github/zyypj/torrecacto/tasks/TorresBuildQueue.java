package com.github.zyypj.torrecacto.tasks;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TorreModel;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Queue;

public class TorresBuildQueue implements Runnable {

    private final Main plugin;
    private final PlotAPI plotAPI;
    private final Queue<TorresBuildTask> queue;
    private final int maxLayersPerTick;

    public TorresBuildQueue(Main plugin) {
        this.plugin = plugin;
        this.plotAPI = new PlotAPI();
        this.queue = new LinkedList<>();
        this.maxLayersPerTick = 5;
    }

    public void addTask(Location location, TorreModel config) {
        TorresBuildTask task = new TorresBuildTask(plugin, location, config);
        queue.add(task);
    }

    public boolean canPlace(Player player, Location baseLocation, int layers) {
        for (int y = 1; y <= layers * 4; y++) {
            Location location = baseLocation.clone().add(0, y, 0);

            if (location.getBlock().getType() != Material.AIR) {
                player.sendMessage(plugin.getConfigManager().getMessage("cant-put"));
                return false;
            }
        }

        Plot plot = plotAPI.getPlot(baseLocation);
        if (plot == null) {
            player.sendMessage(plugin.getConfigManager().getMessage("cant-put"));
            return false;
        }

        if (!plot.isOwner(player.getUniqueId()) && !plot.getTrusted().contains(player.getUniqueId())) {
            player.sendMessage(plugin.getConfigManager().getMessage("cant-put"));
            return false;
        }

        return true;
    }

    @Override
    public void run() {

        if (queue.isEmpty()) return;

        int layersBuilt = 0;

        while (!queue.isEmpty() && layersBuilt < maxLayersPerTick) {
            TorresBuildTask task = queue.peek();

            if (task.buildTower() == 0) {
                queue.poll();
            } else {
                layersBuilt++;
            }
        }
    }
}