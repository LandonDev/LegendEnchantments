package landon.legendenchantments.struct.effects.all;

import landon.legendenchantments.struct.effects.CustomEffect;
import landon.legendenchantments.struct.effects.EffectType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Damage extends CustomEffect {
    public Damage() {
        super("Damage a player/entity", "min amount, max amount, player/target", EffectType.values());
    }

    @Override
    public void execute(Player player, LivingEntity entity, double chance, Object... objects) {
        if(Math.random() < chance) {
            int min = (int) objects[0];
            int max = (int) objects[1];
            String target = (String) objects[2];
            if(target.equalsIgnoreCase("player")) {
                player.damage(ThreadLocalRandom.current().nextInt(min, max + 1));
                return;
            }
            if(entity != null) {
                entity.damage(ThreadLocalRandom.current().nextInt(min, max + 1));
            }
        }
    }

    @Override
    public void stop(Player player, LivingEntity entity, Object... objects) {

    }
}
