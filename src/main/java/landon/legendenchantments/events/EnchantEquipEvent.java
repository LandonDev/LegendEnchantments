package landon.legendenchantments.events;

import landon.legendenchantments.struct.Enchantment;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class EnchantEquipEvent extends Event implements Cancellable {
    public static final HandlerList handlerList = new HandlerList();
    private boolean cancelled;
    private Enchantment enchantment;
    private Player player;

    public EnchantEquipEvent(Player player, Enchantment enchantment) {
        this.cancelled = false;
        this.player = player;
        this.enchantment = enchantment;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = true;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
