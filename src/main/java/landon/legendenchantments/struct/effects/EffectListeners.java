package landon.legendenchantments.struct.effects;

import landon.legendenchantments.struct.EnchantManager;
import landon.legendenchantments.struct.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EffectListeners implements Listener {
    @EventHandler
    public void damageListeners(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            EnchantManager.get().runEffects(player, (LivingEntity) e.getEntity(), EffectType.ON_HIT);
        }
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            EnchantManager.get().runEffects(player, (LivingEntity) e.getDamager(), EffectType.WHEN_HIT);
        }
    }
}
