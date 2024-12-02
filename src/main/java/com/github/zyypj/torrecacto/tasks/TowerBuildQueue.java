package com.github.zyypj.torrecacto.tasks;

import com.github.zyypj.torrecacto.models.TowerConfig;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.LinkedList;
import java.util.Queue;

public class TowerBuildQueue implements Runnable {

    private final Queue<TowerBuildTask> queue;
    private final int maxLayersPerTick;

    public TowerBuildQueue() {
        this.queue = new LinkedList<>();
        this.maxLayersPerTick = 5;
    }

    public void addTask(Location location, TowerConfig config) {
        TowerBuildTask task = new TowerBuildTask(location, config);
        queue.add(task);
    }

    public boolean haveSpace(Location baseLocation, int layers) {
        for (int y = 1; y <= layers * 4; y++) {
            Location location = baseLocation.clone().add(0, y, 0);
            if (location.getBlock().getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        if (queue.isEmpty()) {
            return;
        }

        int layersBuilt = 0;
        while (!queue.isEmpty() && layersBuilt < maxLayersPerTick) {
            TowerBuildTask task = queue.peek();
            System.out.println("Processando construção da torre...");
            if (task.buildTower() == 0) {
                queue.poll();
                System.out.println("Construção da torre concluída.");
            } else {
                layersBuilt++;
            }
        }
    }
}