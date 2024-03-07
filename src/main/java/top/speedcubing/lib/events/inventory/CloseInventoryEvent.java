package top.speedcubing.lib.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseInventoryEvent {

    private final Player player;

    public Player getPlayer() {
        return player;
    }

    private final InventoryCloseEvent event;

    public InventoryCloseEvent getEvent() {
        return event;
    }

    public CloseInventoryEvent(Player player, InventoryCloseEvent event) {
        this.player = player;
        this.event = event;
    }
}