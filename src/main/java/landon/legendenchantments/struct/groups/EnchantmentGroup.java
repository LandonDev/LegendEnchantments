package landon.legendenchantments.struct.groups;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@Getter
@Setter
public class EnchantmentGroup {
    private String internalName;
    private String displayName;
    private ChatColor color;
    private XMaterial xMaterial;

    public EnchantmentGroup(String internalName, String displayName, ChatColor color, XMaterial xMaterial) {
        this.internalName = internalName;
        this.displayName = displayName;
        this.color = color;
        this.xMaterial = xMaterial;
    }
}
