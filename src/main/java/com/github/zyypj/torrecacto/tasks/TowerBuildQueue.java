package com.github.zyypj.torrecacto.tasks;

import com.github.zyypj.torrecacto.models.TowerConfig;
import org.bukkit.Location;

import java.util.LinkedList;
import java.util.Queue;

public class TowerBuildQueue implements Runnable {

    private final Queue<TowerBuildTask> queue;
    private final int maxLayersPerTick;

    public TowerBuildQueue() {
        this.queue = new LinkedList<>();
        this.maxLayersPerTick = 5; // Configurável, limite de camadas por tick
    }

    /**
     * Adiciona uma nova tarefa de construção de torre à fila.
     *
     * @param location Localização base da torre.
     * @param config   Configuração da torre.
     */
    public void addTask(Location location, TowerConfig config) {
        TowerBuildTask task = new TowerBuildTask(location, config);
        queue.add(task);
    }

    /**
     * Verifica se há espaço suficiente para construir a torre.
     *
     * @param baseLocation Localização base onde a torre será construída.
     * @param layers       Número de camadas da torre.
     * @return true se o espaço está disponível, false caso contrário.
     */
    public boolean isSpaceAvailable(Location baseLocation, int layers) {
        for (int y = 0; y < layers * 3; y++) { // Cada camada ocupa 3 blocos
            Location checkLocation = baseLocation.clone().add(0, y, 0);
            if (!isBlockReplaceable(checkLocation)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBlockReplaceable(Location location) {
        switch (location.getBlock().getType()) {
            case AIR:
                return true;
            default:
                return false;
        }
    }

    /**
     * Executa a fila, construindo camadas de forma controlada por tick.
     */
    @Override
    public void run() {
        if (queue.isEmpty()) {
            return;
        }

        int layersBuilt = 0;
        while (!queue.isEmpty() && layersBuilt < maxLayersPerTick) {
            TowerBuildTask task = queue.peek();
            if (task.buildLayer() == 0) {
                queue.poll(); // Remove a tarefa se estiver concluída
            } else {
                layersBuilt++;
            }
        }
    }
}