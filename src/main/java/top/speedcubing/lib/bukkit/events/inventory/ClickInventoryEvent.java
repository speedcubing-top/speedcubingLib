package top.speedcubing.lib.bukkit.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickInventoryEvent {

    private final Player player;

    public Player getPlayer() {
        return player;
    }

    private final InventoryClickEvent event;

    public InventoryClickEvent getEvent() {
        return event;
    }

    public ClickInventoryEvent(Player player, InventoryClickEvent event) {
        this.player = player;
        this.event = event;
    }
}