package top.speedcubing.lib.bukkit.inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.events.inventory.ClickInventoryEvent;
import top.speedcubing.lib.events.inventory.CloseInventoryEvent;

public class InventoryBuilder {
    public static final Set<InventoryBuilder> builderSet = new HashSet<>();
    public final Map<Integer, Consumer<ClickInventoryEvent>> clickInventoryEventMap = new HashMap<>();
    public final boolean[] clickable;
    public boolean deleteOnClose;
    public Consumer<ClickInventoryEvent> allClickEvent;
    public Consumer<CloseInventoryEvent> closeInventoryEvent;
    public final InventoryHolder holder = new InventoryHolder() {
        @Override
        public Inventory getInventory() {
            return inventory;
        }
    };
    private final Inventory inventory;

    public InventoryBuilder(int size, String title) {
        inventory = Bukkit.createInventory(holder, size, title);
        clickable = new boolean[size + 36];
        builderSet.add(this);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryBuilder deleteOnClose(boolean flag) {
        deleteOnClose = flag;
        return this;
    }

    public InventoryBuilder setCloseInventory(Consumer<CloseInventoryEvent> e) {
        this.closeInventoryEvent = e;
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, int... slots) {
        for (int i : slots)
            inventory.setItem(i, stack);
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, int start, int end) {
        for (; start <= end; start++)
            inventory.setItem(start, stack);
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, Consumer<ClickInventoryEvent> event, int... slots) {
        for (int i : slots) {
            inventory.setItem(i, stack);
            clickInventoryEventMap.put(i, event);
        }
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, Consumer<ClickInventoryEvent> event, int start, int end) {
        for (; start <= end; start++) {
            inventory.setItem(start, stack);
            clickInventoryEventMap.put(start, event);
        }
        return this;
    }

    public InventoryBuilder setClickEvent(Consumer<ClickInventoryEvent> event, int... slots) {
        for (int i : slots)
            clickInventoryEventMap.put(i, event);
        return this;
    }

    public InventoryBuilder setClickEvent(Consumer<ClickInventoryEvent> event, int start, int end) {
        for (; start <= end; start++)
            clickInventoryEventMap.put(start, event);
        return this;
    }

    public InventoryBuilder setAllClickEvent(Consumer<ClickInventoryEvent> event) {
        this.allClickEvent = event;
        return this;
    }

    public InventoryBuilder setClickable(boolean flag, int... slots) {
        for (int i : slots)
            clickable[i] = flag;
        return this;
    }

    public InventoryBuilder setClickable(boolean flag, int start, int end) {
        for (; start <= end; start++)
            clickable[start] = flag;
        return this;
    }

    public void delete() {
        builderSet.remove(this);
    }
}
