package landon.legendenchantments;

import fr.minuskube.inv.InventoryManager;
import landon.legendenchantments.struct.EnchantManager;
import landon.legendenchantments.struct.effects.EffectListeners;
import landon.legendenchantments.struct.effects.EffectManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class LegendEnchantments extends JavaPlugin {
    private static LegendEnchantments inst;
    private InventoryManager inventoryManager;
    private boolean debug;

    @Override
    public void onEnable() {
        inst = this;
        saveDefaultConfig();
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.init();
        this.debug = getConfig().getBoolean("debug");
        EnchantManager.get().load();
        EffectManager.get().load();
        listeners();
        commands();
    }

    public void commands() {}

    public void listeners() {
        getServer().getPluginManager().registerEvents(new EffectListeners(), this);
    }

    @Override
    public void onDisable() {
    }

    public static LegendEnchantments get() {
        return inst;
    }
}
