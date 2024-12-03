package com.github.zyypj.torrecacto.tasks;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TorreModel;
import com.github.zyypj.torrecacto.utils.ParticleUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TorresBuildTask {

    private final Main plugin;
    private final Location baseLocation;
    private final TorreModel config;
    private int currentLayer = 0;

    public TorresBuildTask(Main plugin, Location baseLocation, TorreModel config) {
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

        final Location fenceLocation = baseLocation.clone();
        fenceLocation.getWorld().playSound(fenceLocation, Sound.NOTE_PLING, 1f, 0.1f * y);
        fenceLocation.getWorld().getNearbyEntities(fenceLocation, 10, 10, 10).forEach(entity -> {
            if(!(entity instanceof Player)) return;
            for(int i = 0; i < 3; i++) {
                final Location particleLocation = fenceLocation.clone();
                fenceLocation.setY(y + i);
                ParticleUtils.create((Player) entity, EnumParticle.FLAME, particleLocation, 5, 1, 1, 1, 0.2f);
            }
        });

        fenceLocation.setY(y + 3);
        placeBlock(fenceLocation, Material.FENCE);

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

        Bukkit.getScheduler().runTask(plugin, () -> {
            placeBlock(locNorte, material);
            placeBlock(locSul, material);
            placeBlock(locLeste, material);
            placeBlock(locOeste, material);
        });
    }

    private void placeBlock(Location location, Material material) {
        if (location.getBlock().getType() != material) {
            location.getBlock().setType(material);
            location.getBlock().getState().update(true, true);
        }
    }
}
