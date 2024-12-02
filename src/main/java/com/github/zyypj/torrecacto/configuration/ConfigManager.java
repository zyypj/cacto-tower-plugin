package com.github.zyypj.torrecacto.configuration;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TorreConfig;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final Main plugin;
    @Getter
    private final Map<String, TorreConfig> towers;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        towers = new HashMap<>();

        ConfigurationSection towerSection = plugin.getConfig().getConfigurationSection("");
        if (towerSection != null) {
            for (String key : towerSection.getKeys(false)) {
                ConfigurationSection section = towerSection.getConfigurationSection(key);
                if (section != null && section.contains("item.material") && section.contains("item.layers")) {
                    towers.put(key.toLowerCase(), new TorreConfig(section));
                }
            }
        }
    }


    public String getMessage(String key) {
        String message = plugin.getConfig().getString(key.startsWith("messages.") ? key : "messages." + key);
        return message != null ? message.replace("&", "§") : "§cMensagem não encontrada: " + key;
    }

    public TorreConfig getTowerByLayers(int layers) {
        for (TorreConfig config : towers.values()) {
            if (config.getLayers() == layers) {
                return config;
            }
        }
        return null;
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        towers.clear();
        ConfigurationSection towerSection = plugin.getConfig().getConfigurationSection("");
        if (towerSection != null) {
            for (String key : towerSection.getKeys(false)) {
                ConfigurationSection section = towerSection.getConfigurationSection(key);
                if (section != null && section.contains("item.material") && section.contains("item.layers")) {
                    towers.put(key.toLowerCase(), new TorreConfig(section));
                }
            }
        }
    }

    public TorreConfig getTowerConfig(String key) {
        return towers.get(key.toLowerCase());
    }
}