package com.github.zyypj.torrecacto;

import com.github.zyypj.torrecacto.configuration.ConfigManager;
import com.github.zyypj.torrecacto.tasks.TowerBuildQueue;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigManager configManager;

    private TowerBuildQueue towerBuildQueue;

    @Override
    public void onEnable() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        debug(" ", false);
        debug("&eTorreCacto iniciando...", false);

        loadConfig();

        setupManagers();

        debug(" ", false);
        debug("&2&lTorreCacto &2iniciado em " + stopwatch.stop() + "!", false);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void debug(String message, boolean debug) {

        message = message.replace("&", "§");

        if (debug) {
            if (getConfig().getBoolean("debug", true)) {
                Bukkit.getConsoleSender().sendMessage("§8[TORRECACTO-DEBUG] §f" + message);
            }

            return;
        }

        Bukkit.getConsoleSender().sendMessage("§f" + message);
    }

    private void loadConfig() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        debug(" ", true);
        debug("&eIniciando configurações...", true);

        saveDefaultConfig();

        configManager = new ConfigManager(this);

        debug(" ", true);
        debug("&aConfigurações iniciadas em " + stopwatch.stop() + "!", true);
    }

    private void setupManagers() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        debug(" ", true);
        debug("&eCarregando gerenciadores...", true);

        towerBuildQueue = new TowerBuildQueue();
        getServer().getScheduler().runTaskTimer(this, towerBuildQueue, 1L, 1L);

        debug(" ", true);
        debug("&aGerenciadores carregados em " + stopwatch.stop() + "!", true);

    }
}
