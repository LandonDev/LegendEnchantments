package landon.legendenchantments.struct;

import landon.legendenchantments.LegendEnchantments;
import landon.legendenchantments.struct.effects.CustomEffect;
import landon.legendenchantments.struct.effects.EffectManager;
import landon.legendenchantments.struct.groups.EnchantmentGroup;
import landon.legendenchantments.util.EnchantFile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Enchantment {
    private HashMap<Integer, HashMap<CustomEffect, Object[]>> effects = new HashMap<>();
    private String internalName;
    private String displayName;
    private String description;
    private int maxLevel;
    private ApplicableItemType appliesTo;
    private EnchantFile file;
    private EnchantmentGroup group;

    public Enchantment(String internalName, String displayName, int maxLevel, ApplicableItemType appliesTo, EnchantmentGroup group, String description) {
        this.internalName = internalName;
        this.displayName = displayName;
        this.maxLevel = maxLevel;
        this.appliesTo = appliesTo;
        this.group = group;
        this.description = description;
    }

    public void registerEffect(int level, String effect, Object... objects) {
        if(this.effects.containsKey(level)) {
            HashMap<CustomEffect, Object[]> map = new HashMap<>(this.effects.get(level));
            CustomEffect customEffect = EffectManager.get().findEffect(effect);
            map.put(customEffect, objects);
            this.effects.put(level, map);
            return;
        }
        HashMap<CustomEffect, Object[]> map = new HashMap<>();
        CustomEffect customEffect = EffectManager.get().findEffect(effect);
        map.put(customEffect, objects);
        this.effects.put(level, map);
    }

    public Object[] getArgsForEffect(int level, CustomEffect effect) {
        if(this.effects.containsKey(level)) {
            if(this.effects.get(level).containsKey(effect)) {
                return this.effects.get(level).get(effect);
            }
        }
        return null;
    }

    public boolean has(Player player) {
        if(this.getAppliesTo() == ApplicableItemType.ARMOR) {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if(EnchantManager.get().getEnchants(item) != null) {
                    if(EnchantManager.get().getEnchants(item).containsKey(this)) {
                        return true;
                    }
                }
            }
        } else {
            ItemStack item = player.getInventory().getItemInHand();
            if(EnchantManager.get().getEnchants(item) != null) {
                if(EnchantManager.get().getEnchants(item).containsKey(this)) {
                    return true;
                }
            }
        }
        return false;
    }
}
