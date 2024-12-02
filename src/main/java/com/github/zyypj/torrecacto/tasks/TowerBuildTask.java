package com.github.zyypj.torrecacto.tasks;

import com.github.zyypj.torrecacto.models.TowerConfig;
import com.github.zyypj.torrecacto.utils.CustomStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TowerBuildTask {

    private final Location baseLocation;
    private final TowerConfig config;
    private int currentLayer = 0;

    public TowerBuildTask(Location baseLocation, TowerConfig config) {
        this.baseLocation = baseLocation.clone();
        this.config = config;
    }

    public int buildTower() {

        if (currentLayer >= config.getLayers()) return 0;

        int y = baseLocation.getBlockY() + (currentLayer * 3);

        makeLayer(y, getBlock("first-layer"));
        makeLayer(y + 1, getBlock("second-layer"));
        makeLayer(y + 2, getBlock("third-layer"));

        if (currentLayer == config.getLayers() - 1) {
            Location fenceLocation = baseLocation.clone().add(0, y + 3, 0);
            fenceLocation.getBlock().setType(getBlock("fourth-layer"));
        }

        currentLayer++;
        return 1;
    }

    private void makeLayer(int y, Material material) {
        baseLocation.clone().add(0, y, 1).getBlock().setType(material); // Norte
        baseLocation.clone().add(0, y, -1).getBlock().setType(material); // Sul
        baseLocation.clone().add(1, y, 0).getBlock().setType(material); // Leste
        baseLocation.clone().add(-1, y, 0).getBlock().setType(material); // Oeste
    }

    private Material getBlock(String layerKey) {
        String materialString = config.getConfig().getString(layerKey);
        ItemStack stack = CustomStack.get(materialString);
        if (stack != null) {
            return stack.getType();
        }
        return Material.AIR;
    }
}
