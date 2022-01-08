package landon.legendenchantments.struct;

import com.cryptomorin.xseries.XMaterial;
import com.sun.javaws.jnl.RContentDesc;
import de.tr7zw.changeme.nbtapi.NBTItem;
import landon.legendenchantments.LegendEnchantments;
import landon.legendenchantments.struct.effects.CustomEffect;
import landon.legendenchantments.struct.effects.EffectType;
import landon.legendenchantments.struct.groups.EnchantmentGroup;
import landon.legendenchantments.util.EnchantFile;
import lombok.Getter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

@Getter
public class EnchantManager {
    private static volatile EnchantManager inst;

    private List<Enchantment> enchantments = new ArrayList<>();
    private List<EnchantmentGroup> groups = new ArrayList<>();

    private EnchantManager() {}

    public static EnchantManager get() {
        if(inst == null) {
            synchronized (EnchantManager.class) {
                inst = new EnchantManager();
            }
        }
        return inst;
    }

    public void load() {
        this.registerGroups();
    }

    public Enchantment getEnchantment(String name, boolean displayName, boolean ignoreCase) {
        for (Enchantment enchantment : this.enchantments) {
            if(displayName ? (ignoreCase ? enchantment.getDisplayName().equalsIgnoreCase(name) : enchantment.getDisplayName().equals(name)) : (ignoreCase ? enchantment.getInternalName().equalsIgnoreCase(name) : enchantment.getInternalName().equals(name))) {
                return enchantment;
            }
        }
        return null;
    }

    public void registerGroups() {
        Configuration config = LegendEnchantments.get().getConfig();
        if(config.isSet("groups")) {
            ConfigurationSection section = config.getConfigurationSection("groups");
            for (String key : config.getConfigurationSection("groups").getKeys(false)) {
                this.groups.add(new EnchantmentGroup(key, section.getString(key + ".display"), ChatColor.valueOf(section.getString(key + ".color")), XMaterial.matchXMaterial(section.getString(key + ".material")).get()));
                LegendEnchantments.get().getLogger().log(Level.INFO, "Loaded enchantment group: " + key);
            }
        }
        if(this.groups.isEmpty()) {
            LegendEnchantments.get().getLogger().log(Level.SEVERE, "No Enchantment groups were found in the config, shutting down plugin!");
            Bukkit.getPluginManager().disablePlugin(LegendEnchantments.get());
            LegendEnchantments.get().onDisable();
        }
    }

    /*public Enchantment findEnchantment(CustomEffect effect) {
        for (Enchantment enchantment : this.enchantments) {
            for (CustomEffect enchantmentEffect : enchantment.getEffects()) {
                if(enchantmentEffect == effect) {
                    return enchantment;
                }
            }
        }
        return null;
    }*/

    public HashMap<Enchantment, Integer> getEnchants(ItemStack item) {
        if(item != null && item.getType() == Material.AIR) {
            HashMap<Enchantment, Integer> map = new HashMap<>();
            NBTItem nbtItem = new NBTItem(item);
            if(nbtItem.hasKey("enchantments")) {
                for (String enchantString : nbtItem.getStringList("enchantments")) {
                    Enchantment enchant = this.getEnchantment(enchantString.split(":")[0], false, false);
                    if(!NumberUtils.isNumber(enchantString.split(":")[1])) {
                        LegendEnchantments.get().getLogger().log(Level.SEVERE, "Item had enchantment that had a level that wasn't a number!");
                        continue;
                    }
                    if(enchant == null) {
                        LegendEnchantments.get().getLogger().log(Level.SEVERE, "Item had enchantment that didn't exist!");
                        continue;
                    }
                    int level = Integer.parseInt(enchantString.split(":")[1]);
                    map.put(enchant, level);
                }
            }
            return map;
        }
        return null;
    }

    public HashMap<Enchantment, Integer> getEnchants(Player player) {
        HashMap<Enchantment, Integer> map = new HashMap<>();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if(this.getEnchants(item) != null) {
                this.getEnchants(item).forEach((enchant, level) -> {
                    if(!map.containsKey(enchant)) {
                        map.put(enchant, level);
                    } else {
                        if(level > map.get(enchant)) {
                            map.put(enchant, level);
                        }
                    }
                });
            }
        }
        ItemStack item = player.getInventory().getItemInHand();
        this.getEnchants(item).forEach((enchant, level) -> {
            if(!map.containsKey(enchant)) {
                map.put(enchant, level);
            } else {
                if(level > map.get(enchant)) {
                    map.put(enchant, level);
                }
            }
        });
        return map;
    }

    private boolean typeMatches(EffectType[] types, EffectType[] types2) {
        for (EffectType type : types) {
            for (EffectType effectType : types2) {
                if(type == effectType) {
                    return true;
                }
            }
        }
        return false;
    }

    public void runEffects(Player player, LivingEntity entity, EffectType... types) {
        this.getEnchants(player).forEach((enchant, level) -> {
            if(enchant.getEffects().containsKey(level)) {
                enchant.getEffects().get(level).forEach((effect, objects) -> {
                    if(this.typeMatches(types, effect.getType())) {
                        effect.execute(player, entity, (double)objects[0], ArrayUtils.remove(objects, 0));
                    }
                });
            }
        });
    }

    public Enchantment registerEnchantment(String internalName, String displayName, int maxLevel, ApplicableItemType appliesTo, String description, EnchantmentGroup group) {
        Enchantment enchantment = new Enchantment(internalName, displayName, maxLevel, appliesTo, group, description);
        enchantment.setFile(new EnchantFile(LegendEnchantments.get(), enchantment.getInternalName() + ".yml"));
        this.enchantments.add(enchantment);
        LegendEnchantments.get().getLogger().log(Level.INFO, "Registered enchantment: " + enchantment.getInternalName());
        return enchantment;
    }

    public void saveEnchantment(Enchantment enchantment) {
        Configuration config = enchantment.getFile().get();
        config.set("max-level", enchantment.getMaxLevel());
        config.set("display-name", enchantment.getDisplayName());
        config.set("applies-to", enchantment.getAppliesTo());
        config.set("description", enchantment.getDescription());
        enchantment.getEffects().forEach((level, effects) -> {
            effects.forEach((effect, objects) -> {
                for (Object object : objects) {
                    config.set("effects." + level + "." + effect.getName() + ".variables", object.toString());
                }
            });
        });
    }
}
