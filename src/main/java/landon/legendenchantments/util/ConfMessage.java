package landon.legendenchantments.util;

import landon.legendenchantments.LegendEnchantments;
import landon.legendenchantments.struct.EnchantManager;
import landon.legendenchantments.struct.Enchantment;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ConfMessage {
    public static String parse(String path, Object... objects) {
        String message = c.c(LegendEnchantments.get().getConfig().getString("messages." + path));
        for (Object object : objects) {
            if (object instanceof Player) {
                message = message.replace("%player%", ((Player) object).getName());
            }
            if (object instanceof Enchantment) {
                message = message.replace("%enchant-display%", ((Enchantment) object).getDisplayName())
                        .replace("%enchant-internal%", ((Enchantment) object).getInternalName());
            }
            if (object instanceof PotionEffect) {
                message = message.replace("%potion-effect%", ((PotionEffect) object).getType().getName())
                        .replace("%amplifier%", ((PotionEffect) object).getAmplifier() + "")
                        .replace("%duration%", ((PotionEffect) object).getDuration() + "");
            }
            if (object instanceof String) {
                String string = (String) object;
                if (string.contains(":") && EnchantManager.get().getEnchantment(string.split(":")[0], false, false) != null && NumberUtils.isNumber(string.split(":")[1])) {
                    Enchantment enchant = EnchantManager.get().getEnchantment(string.split(":")[0], false, false);
                    int level = Integer.parseInt(string.split(":")[1]);
                    message = message.replace("%enchant-display%", enchant.getDisplayName())
                            .replace("%enchant-internal%", enchant.getInternalName())
                            .replace("%level%", level + "");
                }
            }
        }
        return message;
    }

    public static void send(Player player, String path, Object... objects) {
        String message = c.c(LegendEnchantments.get().getConfig().getString("messages." + path));
        for (Object object : objects) {
            if (object instanceof Player) {
                message = message.replace("%player%", ((Player) object).getName());
            }
            if (object instanceof Enchantment) {
                message = message.replace("%enchant-display%", ((Enchantment) object).getDisplayName())
                        .replace("%enchant-internal%", ((Enchantment) object).getInternalName());
            }
            if (object instanceof PotionEffect) {
                message = message.replace("%potion-effect%", ((PotionEffect) object).getType().getName())
                        .replace("%amplifier%", ((PotionEffect) object).getAmplifier() + "")
                        .replace("%duration%", ((PotionEffect) object).getDuration() + "");
            }
            if (object instanceof String) {
                String string = (String) object;
                if (string.contains(":") && EnchantManager.get().getEnchantment(string.split(":")[0], false, false) != null && NumberUtils.isNumber(string.split(":")[1])) {
                    Enchantment enchant = EnchantManager.get().getEnchantment(string.split(":")[0], false, false);
                    int level = Integer.parseInt(string.split(":")[1]);
                    message = message.replace("%enchant-display%", enchant.getDisplayName())
                            .replace("%enchant-internal%", enchant.getInternalName())
                            .replace("%level%", level + "");
                }
            }
        }
        if(message.contains("none")) {
            return;
        }
        player.sendMessage(message);
    }

    public static void sendConfigString(Player player, String path, Object... objects) {
        String message = c.c(LegendEnchantments.get().getConfig().getString(path));
        for (Object object : objects) {
            if (object instanceof Player) {
                message = message.replace("%player%", ((Player) object).getName());
            }
            if (object instanceof Enchantment) {
                message = message.replace("%enchant-display%", ((Enchantment) object).getDisplayName())
                        .replace("%enchant-internal%", ((Enchantment) object).getInternalName());
            }
            if (object instanceof PotionEffect) {
                message = message.replace("%potion-effect%", ((PotionEffect) object).getType().getName())
                        .replace("%amplifier%", ((PotionEffect) object).getAmplifier() + "")
                        .replace("%duration%", ((PotionEffect) object).getDuration() + "");
            }
            if (object instanceof String) {
                String string = (String) object;
                if (string.contains(":") && EnchantManager.get().getEnchantment(string.split(":")[0], false, false) != null && NumberUtils.isNumber(string.split(":")[1])) {
                    Enchantment enchant = EnchantManager.get().getEnchantment(string.split(":")[0], false, false);
                    int level = Integer.parseInt(string.split(":")[1]);
                    message = message.replace("%enchant-display%", enchant.getDisplayName())
                            .replace("%enchant-internal%", enchant.getInternalName())
                            .replace("%level%", level + "");
                }
            }
        }
        if(message.contains("none")) {
            return;
        }
        player.sendMessage(message);
    }

    public static String parseConfigString(String path, Object... objects) {
        String message = c.c(LegendEnchantments.get().getConfig().getString(path));
        for (Object object : objects) {
            if (object instanceof Player) {
                message = message.replace("%player%", ((Player) object).getName());
            }
            if (object instanceof Enchantment) {
                message = message.replace("%enchant-display%", ((Enchantment) object).getDisplayName())
                        .replace("%enchant-internal%", ((Enchantment) object).getInternalName());
            }
            if (object instanceof PotionEffect) {
                message = message.replace("%potion-effect%", ((PotionEffect) object).getType().getName())
                        .replace("%amplifier%", ((PotionEffect) object).getAmplifier() + "")
                        .replace("%duration%", ((PotionEffect) object).getDuration() + "");
            }
            if (object instanceof String) {
                String string = (String) object;
                if (string.contains(":") && EnchantManager.get().getEnchantment(string.split(":")[0], false, false) != null && NumberUtils.isNumber(string.split(":")[1])) {
                    Enchantment enchant = EnchantManager.get().getEnchantment(string.split(":")[0], false, false);
                    int level = Integer.parseInt(string.split(":")[1]);
                    message = message.replace("%enchant-display%", enchant.getDisplayName())
                            .replace("%enchant-internal%", enchant.getInternalName())
                            .replace("%level%", level + "");
                }
            }
        }
        return message;
    }
}
