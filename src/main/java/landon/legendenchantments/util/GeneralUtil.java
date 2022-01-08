package landon.legendenchantments.util;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import landon.legendenchantments.LegendEnchantments;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GeneralUtil {

    public static Material[] helmets() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_HELMET")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static Material[] chestplates() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_CHESTPLATE")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static Material[] leggings() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_LEGGINGS")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static Material[] boots() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_BOOTS")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static Material[] swords() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_SWORD")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static Material[] axes() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_AXE")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static Material[] pickaxes() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_PICKAXE")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static Material[] hoes() {
        List<Material> materials = Lists.newArrayList();
        for (Material value : Material.values()) {
            if(value.toString().endsWith("_HOE")) {
                materials.add(value);
            }
        }
        return materials.toArray(new Material[materials.size()]);
    }

    public static String getConfigString(String path) {
        return c.c(LegendEnchantments.get().getConfig().getString(path));
    }

    public static FileConfiguration storeItem(FileConfiguration config, String path, ItemStack stack) {
        config.set(path + ".material", stack.getType().toString());
        config.set(path + ".amount", stack.getAmount());
        config.set(path + ".nbt", NBTItem.convertItemtoNBT(stack).toString());
        return config;
    }

    public static ItemStack getStoredItem(FileConfiguration config, String path) {
        Material material = XMaterial.matchXMaterial(config.getString(path + ".material")).get().parseMaterial();
        int amount = config.getInt(path + ".amount");
        String nbt = config.getString(path + ".nbt");
        ItemStack stack = NBTItem.convertNBTtoItem(new NBTContainer(nbt));
        stack.setType(material);
        stack.setAmount(amount);
        return stack;
    }

    public static String getDisplayName(ItemStack physicalItem) {
        return (physicalItem.hasItemMeta() ? (physicalItem.getItemMeta().hasDisplayName() ? physicalItem.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords(physicalItem.getType().toString().toLowerCase().replace("_", " "))) : StringUtils.capitaliseAllWords(physicalItem.getType().toString().toLowerCase().replace("_", " ")));
    }

    public static void giveOrDropItem(Player player, ItemStack item) {
        int amountOfItems = 0;
        for(int i = 0; i < 36; i++) {
            if(player.getInventory().getItem(i) != null) {
                amountOfItems++;
            }
        }
        if(amountOfItems >= 36) {
            player.getWorld().dropItem(player.getLocation(), item);
            player.sendMessage(c.c("&c&l(!) &cYour inventory was full so your item was dropped in front of you! (" + (item.hasItemMeta() ? (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : org.apache.commons.lang.StringUtils.capitaliseAllWords(item.getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' '))) : org.apache.commons.lang.StringUtils.capitaliseAllWords(item.getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' '))) + "&c)"));
        } else {
            player.getInventory().addItem(item);
        }
    }

    public static List<String> getAndModifyList(List<String> currentList, String... toAdd) {
        List<String> list = new ArrayList<>(currentList);
        for (String s : toAdd) {
            list.add(c.c(s));
        }
        return list;
    }

    public static List<String> getAndModifyLore(ItemStack item, String... toAdd) {
        List<String> lore;
        if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
            lore = new ArrayList<>(item.getItemMeta().getLore());
        } else {
            lore = new ArrayList<>();
        }
        for (String s : toAdd) {
            lore.add(c.c(s));
        }
        return lore;
    }

    public static ItemStack createItem(Material material, String name, int amount, int data, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short)data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color(name));
        meta.setLore(color(Lists.newArrayList(lore)));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, String... lore) {
        return createItem(material, name, 1, 0, lore);
    }

    public static ItemStack createItem(ItemStack item, String name, String... lore) {
        return modifyItem(item, name, color(Lists.newArrayList(lore)));
    }

    public static ItemStack createItem(ItemStack item, String name, List<String> lore) {
        return modifyItem(item, name, color(lore));
    }

    public static ItemStack createItem(Material material, String name, int amount, String... lore) {
        return createItem(material, name, amount, 1, lore);
    }

    public static ItemStack createItem(Material material, String name, int amount, int data, List<String> lore) {
        return createItem(material, name, amount, data, lore.<String>toArray(new String[lore.size()]));
    }

    public static ItemStack createItem(Material material, String name, List<String> lore) {
        return createItem(material, name, lore.<String>toArray(new String[lore.size()]));
    }

    public static ItemStack modifyItem(ItemStack item, ItemMeta meta) {
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack modifyItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color(name));
        return modifyItem(item, meta);
    }

    public static ItemStack modifyItem(ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(color(name));
        if (lore != null)
            meta.setLore(color(lore));
        return modifyItem(item, meta);
    }

    public static int roundInventorySize(int count) {
        return (count + 8) / 9 * 9;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String stripColor(String message) {
        return ChatColor.stripColor(message);
    }

    public static List<String> color(List<String> messages, ChatColor color) {
        return (List<String>)messages.stream().map(str -> color + str).collect(Collectors.toList());
    }

    public static List<String> color(List<String> messages) {
        return (List<String>)messages.stream().map(GeneralUtil::color).collect(Collectors.toList());
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public static void sendMessage(CommandSender sender, List<String> messages) {
        sender.sendMessage(color(messages).<String>toArray(new String[messages.size()]));
    }

    public static boolean hasArmorOn(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null)
                if (item != null && item.getType() != Material.AIR)
                    return true;
        }
        return false;
    }

    public static double extractDouble(String original) {
        String raw = ChatColor.stripColor(original);
        raw = raw.replaceAll("[\\s+a-zA-Z :]", "").replace("%", "");
        return tryParseDouble(raw);
    }

    public static int extractInt(String original) {
        String raw = ChatColor.stripColor(original);
        return tryParseInt(raw.replaceAll("[^0-9]", ""));
    }

    public static int tryParseInt(String unparsed) {
        try {
            return Integer.parseInt(unparsed);
        } catch (Exception e) {
            return -1;
        }
    }

    public static double tryParseDouble(String unparsed) {
        try {
            return Double.parseDouble(unparsed);
        } catch (Exception e) {
            return -1.0D;
        }
    }

    public static long tryParseLong(String unparsed) {
        try {
            return Long.parseLong(unparsed);
        } catch (Exception e) {
            return -1L;
        }
    }
}
