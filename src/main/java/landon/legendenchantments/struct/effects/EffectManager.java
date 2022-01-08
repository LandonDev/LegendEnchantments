package landon.legendenchantments.struct.effects;

import landon.legendenchantments.struct.EnchantManager;
import landon.legendenchantments.struct.effects.all.Damage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
public class EffectManager {
    private static volatile EffectManager inst;

    private List<CustomEffect> registeredEffects = new ArrayList<>();

    private EffectManager() {}

    public static EffectManager get() {
        if(inst == null) {
            synchronized (EnchantManager.class) {
                inst = new EffectManager();
            }
        }
        return inst;
    }

    public void load() {
        CustomEffect[] effects = new CustomEffect[]{
                new Damage()
        };
        Arrays.stream(effects).forEach(effect -> this.registeredEffects.add(effect));
    }

    public CustomEffect findEffect(String name) {
        for (CustomEffect effect : this.registeredEffects) {
            if(effect.getClass().getSimpleName().equalsIgnoreCase(name)) {
                return effect;
            }
        }
        return null;
    }
}
