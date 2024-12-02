package com.github.zyypj.torrecacto.tasks;

import com.github.zyypj.torrecacto.models.TowerConfig;
import org.bukkit.Location;
import org.bukkit.Material;

public class TowerBuildTask {

    private final Location baseLocation;
    private final TowerConfig config;
    private int currentLayer = 0;

    public TowerBuildTask(Location baseLocation, TowerConfig config) {
        this.baseLocation = baseLocation.clone();
        this.config = config;
    }

    public int buildLayer() {

        if (currentLayer >= config.getLayers()) return 0;

        int y = baseLocation.getBlockY() + (currentLayer * 3);

        buildLayer(y, config.getFirstLayerMaterial().getType());
        buildLayer(y + 1, config.getSecondLayerMaterial().getType());
        buildLayer(y + 2, config.getThirdLayerMaterial().getType());

        if (currentLayer == config.getLayers() - 1) {
            placeBlock(baseLocation.clone().add(0, y + 3, 0), config.getFourthLayerMaterial().getType());
        }

        currentLayer++;
        return 1;
    }

    private void buildLayer(int y, Material material) {
        placeBlock(baseLocation.clone().add(1, y, 0), material); // Leste
        placeBlock(baseLocation.clone().add(-1, y, 0), material); // Oeste
        placeBlock(baseLocation.clone().add(0, y, 1), material); // Norte
        placeBlock(baseLocation.clone().add(0, y, -1), material); // Sul
    }

    private void placeBlock(Location location, Material material) {
        location.getBlock().setType(material);
    }
}
