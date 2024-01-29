package top.speedcubing.lib.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickInventoryEvent {
    void run(Player player, InventoryClickEvent bukkitEvent);
}
