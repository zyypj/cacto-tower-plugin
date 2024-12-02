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

    public int buildTower() {

        if (currentLayer >= config.getLayers()) return 0;

        int y = baseLocation.getBlockY() + (currentLayer * 4); // Cada layer agora ocupa 4 blocos de altura.

        System.out.println("Construindo camada na altura: " + y);

        makeLayer(y, Material.STONE);  // Pedra
        makeLayer(y + 1, Material.SAND);  // Areia
        makeLayer(y + 2, Material.CACTUS);  // Cacto
        makeLayer(y + 3, Material.FENCE);  // Cerca no final da layer

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
        if (location.getBlock().getType() != material) {
            location.getBlock().setType(material);
            location.getBlock().getState().update(true, true);
            System.out.println("Bloco atualizado em " + location + " com material: " + material);
        }
    }
}
