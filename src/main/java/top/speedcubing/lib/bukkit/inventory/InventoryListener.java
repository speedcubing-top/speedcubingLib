package top.speedcubing.lib.bukkit.inventory;

import java.util.HashMap;
import java.util.function.Consumer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.speedcubing.lib.bukkit.events.inventory.ClickInventoryEvent;
import top.speedcubing.lib.bukkit.events.inventory.CloseInventoryEvent;

public class InventoryListener {
    static final HashMap<InventoryBuilder, Player> inventoryMap = new HashMap<>();

    public static void playerQuitEvent(PlayerQuitEvent e) {
        InventoryListener.inventoryMap.entrySet().removeIf(entry -> entry.getValue() == e.getPlayer());
    }

    public static void inventoryClickEvent(InventoryClickEvent e) {
        int slot = e.getRawSlot();

        Consumer<ClickInventoryEvent> clickEvent;
        for (InventoryBuilder b : InventoryListener.inventoryMap.keySet()) {
            if (b.getInventory().hashCode() != e.getClickedInventory().hashCode()) {
                continue;
            }

            clickEvent = b.allClickEvent;
            if (clickEvent != null) {
                clickEvent.accept(new ClickInventoryEvent((Player) e.getWhoClicked(), e, b));
            }

            clickEvent = b.clickEvents.get(slot);
            if (clickEvent != null) {
                clickEvent.accept(new ClickInventoryEvent((Player) e.getWhoClicked(), e, b));
            }

            if (!b.clickable[slot]) {
                e.setCancelled(true);
            }
            break;
        }
    }

    public static void inventoryCloseEvent(InventoryCloseEvent e) {
        for (InventoryBuilder b : InventoryListener.inventoryMap.keySet()) {
            if (b.getInventory().hashCode() != e.getInventory().hashCode()) {
                continue;
            }
            if (b.closeInventoryEvent != null)
                b.closeInventoryEvent.accept(new CloseInventoryEvent((Player) e.getPlayer(), e, b));
            if (b.isTempInventory()) {
                b.delete();
            }
        }
    }
}
