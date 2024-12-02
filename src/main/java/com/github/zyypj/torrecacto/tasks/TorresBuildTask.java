package com.github.zyypj.torrecacto.tasks;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TorreConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class TorresBuildTask {

    private final Main plugin;
    private final Location baseLocation;
    private final TorreConfig config;
    private int currentLayer = 0;

    public TorresBuildTask(Main plugin, Location baseLocation, TorreConfig config) {
        this.plugin = plugin;
        this.baseLocation = baseLocation.clone();
        this.config = config;
    }

    public int buildTower() {

        if (currentLayer >= config.getLayers()) return 0;

        int y = baseLocation.getBlockY() + (currentLayer * 4);

        makeLayer(y, Material.STONE);
        makeLayer(y + 1, Material.SAND);
        makeLayer(y + 2, Material.CACTUS);
        makeLayer(y + 3, Material.FENCE);

        currentLayer++;

        return 1;
    }

    private void makeLayer(int y, Material material) {
        Location locNorte = baseLocation.clone().add(0, 0, 1);
        locNorte.setY(y);
        Location locSul = baseLocation.clone().add(0, 0, -1);
        locSul.setY(y);
        Location locLeste = baseLocation.clone().add(1, 0, 0);
        locLeste.setY(y);
        Location locOeste = baseLocation.clone().add(-1, 0, 0);
        locOeste.setY(y);

        placeBlock(locNorte, material);
        placeBlock(locSul, material);
        placeBlock(locLeste, material);
        placeBlock(locOeste, material);
    }

    private void placeBlock(Location location, Material material) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (location.getBlock().getType() != material) {
                location.getBlock().setType(material);
                location.getBlock().getState().update(true, true);
            }
        });
    }
}
