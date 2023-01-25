package top.speedcubing.lib.bukkit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InventoryBuilder {
    public static final Set<InventoryBuilder> builderSet = new HashSet<>();
    public final Map<Integer, ClickInventoryEvent> clickInventoryEventMap = new HashMap<>();
    public final boolean[] clickable;
    public boolean deleteOnClose;
    public final InventoryHolder holder = new InventoryHolder() {
        @Override
        public Inventory getInventory() {
            return inventory;
        }
    };
    public final Inventory inventory;

    public InventoryBuilder(int size, String title) {
        inventory = Bukkit.createInventory(holder, size, title);
        clickable = new boolean[size];
        builderSet.add(this);
    }

    public InventoryBuilder deleteOnClose(boolean flag) {
        deleteOnClose = flag;
        return this;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryBuilder setItem(ItemStack stack, int slot) {
        inventory.setItem(slot, stack);
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

    public InventoryBuilder setItem(ItemStack stack, ClickInventoryEvent event, int slot) {
        inventory.setItem(slot, stack);
        clickInventoryEventMap.put(slot, event);
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, ClickInventoryEvent event, int... slots) {
        for (int i : slots) {
            inventory.setItem(i, stack);
            clickInventoryEventMap.put(i, event);
        }
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, ClickInventoryEvent event, int start, int end) {
        for (; start <= end; start++) {
            inventory.setItem(start, stack);
            clickInventoryEventMap.put(start, event);
        }
        return this;
    }

    public InventoryBuilder setClickEvent(ClickInventoryEvent event, int slot) {
        clickInventoryEventMap.put(slot, event);
        return this;
    }

    public InventoryBuilder setClickEvent(ClickInventoryEvent event, int... slots) {
        for (int i : slots)
            clickInventoryEventMap.put(i, event);
        return this;
    }

    public InventoryBuilder setClickEvent(ClickInventoryEvent event, int start, int end) {
        for (; start <= end; start++)
            clickInventoryEventMap.put(start, event);
        return this;
    }

    public InventoryBuilder setDraggable(boolean flag, int slot) {
        clickable[slot] = flag;
        return this;
    }

    public InventoryBuilder setDraggable(boolean flag, int... slots) {
        for (int i : slots)
            clickable[i] = flag;
        return this;
    }

    public InventoryBuilder setDraggable(boolean flag, int start, int end) {
        for (; start <= end; start++)
            clickable[start] = flag;
        return this;
    }

    public void delete() {
        builderSet.remove(this);
    }
}
