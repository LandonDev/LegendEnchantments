package landon.legendenchantments.struct.effects;

import landon.legendenchantments.struct.ApplicableItemType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@Getter
public abstract class CustomEffect {
    private String description;
    private String needs;
    private EffectType[] type;

    public CustomEffect(String description, String needs, EffectType... type) {
        this.description = description;
        this.needs = needs;
        this.type = type;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public abstract void execute(Player player, LivingEntity entity, double chance, Object... objects);
    public abstract void stop(Player player, LivingEntity entity, Object... objects);
}
