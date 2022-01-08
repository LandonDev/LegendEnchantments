package landon.legendenchantments.struct;

import landon.legendenchantments.util.GeneralUtil;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Set;

@Getter
public enum ApplicableItemType {
    ARMOR(GeneralUtil.helmets(), GeneralUtil.chestplates(), GeneralUtil.leggings(), GeneralUtil.boots()),
    HELMETS(GeneralUtil.helmets()),
    CHESTPLATE(GeneralUtil.chestplates()),
    LEGGINGS(GeneralUtil.leggings()),
    BOOTS(GeneralUtil.boots()),
    SWORDS(GeneralUtil.swords()),
    AXES(GeneralUtil.axes()),
    WEAPONS(GeneralUtil.swords(), GeneralUtil.axes()),
    PICKAXES(GeneralUtil.pickaxes()),
    HOES(GeneralUtil.hoes()),
    TOOLS(GeneralUtil.hoes(), GeneralUtil.pickaxes(), GeneralUtil.axes());

    private Set<Material> materials;
    ApplicableItemType(Material[]... materials) {
        for (Material[] material : materials) {
            for (Material material1 : material) {
                this.materials.add(material1);
            }
        }
    }
}
