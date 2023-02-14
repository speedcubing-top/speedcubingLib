package top.speedcubing.lib.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface CloseInventoryEvent {
    void run(Player player, InventoryCloseEvent bukkitEvent);
}
