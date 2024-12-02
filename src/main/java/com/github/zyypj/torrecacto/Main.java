package com.github.zyypj.torrecacto;

import com.github.zyypj.torrecacto.command.TorresCommand;
import com.github.zyypj.torrecacto.configuration.ConfigManager;
import com.github.zyypj.torrecacto.listeners.TorresPlaceListener;
import com.github.zyypj.torrecacto.tasks.TorresBuildQueue;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigManager configManager;

    private TorresBuildQueue torresBuildQueue;

    @Override
    public void onEnable() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        debug(" ", false);
        debug("&eTorreCacto iniciando...", false);

        loadConfig();

        setupManagers();

        registerListeners();
        registerCommands();

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

        debug("&aConfigurações iniciadas em " + stopwatch.stop() + "!", true);
    }

    private void setupManagers() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        debug(" ", true);
        debug("&eCarregando gerenciadores...", true);

        torresBuildQueue = new TorresBuildQueue(this);
        getServer().getScheduler().runTaskTimer(this, torresBuildQueue, 1L, 1L);

        debug("&aGerenciadores carregados em " + stopwatch.stop() + "!", true);

    }

    private void registerListeners() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        debug(" ", true);
        debug("&eRegistrando eventos..", true);

        getServer().getPluginManager().registerEvents(new TorresPlaceListener(this), this);

        debug("&aEventos registrados em " + stopwatch.stop() + "!", true);

    }

    private void registerCommands() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        debug(" ", true);
        debug("&eRegistrando comandos...", true);

        getCommand("torres").setExecutor(new TorresCommand(this));

        debug("&aComandos registrados em " + stopwatch.stop() + "!", true);
        debug(" ", true);

    }
}
