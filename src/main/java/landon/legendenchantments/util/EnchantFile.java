package landon.legendenchantments.util;

import landon.legendenchantments.LegendEnchantments;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class EnchantFile {
    private FileConfiguration config;

    private File file;

    private Plugin plugin;

    private String fileName;

    public EnchantFile(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        create();
    }

    public FileConfiguration get() {
        return this.config;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        this.file.delete();
    }

    public void create() {
        this.file = new File(this.plugin.getDataFolder() + "/enchantments/", getFileName());
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
        }
        this.config = (FileConfiguration) new YamlConfiguration();
        try {
            this.config.load(this.file);
        } catch(FileNotFoundException e) {
            if(LegendEnchantments.get().isDebug()) {
                e.printStackTrace();
            }
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.config = (FileConfiguration) new YamlConfiguration();
        try {
            this.config.load(this.file);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
