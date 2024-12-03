package com.github.zyypj.torrecacto.configuration;

import com.github.zyypj.torrecacto.Main;
import com.github.zyypj.torrecacto.models.TorreModel;
import com.github.zyypj.torrecacto.utils.CustomStack;
import com.github.zyypj.torrecacto.utils.ItemBuilder;
import com.github.zyypj.torrecacto.utils.Text;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final Main plugin;
    @Getter
    private final Map<String, TorreModel> towers = new HashMap<>();

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        loadTorres();
    }

    public void loadTorres() {
        final FileConfiguration config = plugin.getConfig();
        plugin.getConfig().getConfigurationSection("torres").getKeys(false).forEach(key -> {
            towers.put(key.toLowerCase(), new TorreModel(
                    key.toLowerCase(),
                    new ItemBuilder(CustomStack.get(config.getString(String.format("torres.%s.item.material", key))))
                            .displayname(Text.colorTranslate(config.getString(String.format("torres.%s.item.name", key))))
                            .lore(Text.colorTranslate(config.getStringList(String.format("torres.%s.item.lore", key))))
                            .build(),
                    config.getInt(String.format("torres.%s.layers", key))
            ));
        });

        plugin.debug(String.format("§bForam carregados %s torres.", towers.size()), true);
    }


    public String getMessage(String key) {
        String message = plugin.getConfig().getString(key.startsWith("messages.") ? key : "messages." + key);
        return message != null ? message.replace("&", "§") : "§cMensagem não encontrada: " + key;
    }

    public TorreModel getTowerByLayers(int layers) {
        for (TorreModel config : towers.values()) {
            if (config.getLayers() == layers) {
                return config;
            }
        }
        return null;
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        loadTorres();
    }

    public TorreModel getTowerConfig(String key) {
        return towers.get(key.toLowerCase());
    }
}