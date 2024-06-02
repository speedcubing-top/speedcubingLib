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
import top.speedcubing.lib.bukkit.events.inventory.ClickInventoryEvent;
import top.speedcubing.lib.bukkit.events.inventory.CloseInventoryEvent;
import top.speedcubing.lib.utils.ReflectionUtils;

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

    public void setTitle(String title) {
        ReflectionUtils.setField(ReflectionUtils.getSuperField(inventory, "inventory"), "title", title);
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

    public InventoryBuilder setItem(ItemStack stack, int slot) {
        inventory.setItem(slot, stack);
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, int... slots) {
        for (int i : slots)
            setItem(stack, i);
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, int start, int end) {
        for (; start <= end; start++)
            inventory.setItem(start, stack);
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, Consumer<ClickInventoryEvent> event, int slot) {
        inventory.setItem(slot, stack);
        clickInventoryEventMap.put(slot, event);
        return this;
    }

    public InventoryBuilder setItem(ItemStack stack, Consumer<ClickInventoryEvent> event, int... slots) {
        for (int i : slots) {
            setItem(stack, event, i);
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

    public InventoryBuilder setClickEvent(Consumer<ClickInventoryEvent> event, int slot) {
        clickInventoryEventMap.put(slot, event);
        return this;
    }

    public InventoryBuilder setClickEvent(Consumer<ClickInventoryEvent> event, int... slots) {
        for (int i : slots)
            setClickEvent(event, i);
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

    public InventoryBuilder setClickable(boolean flag, int slot) {
        clickable[slot] = flag;
        return this;
    }

    public InventoryBuilder setClickable(boolean flag, int... slots) {
        for (int i : slots)
            setClickable(flag, i);
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

//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent e) {
//        if (e.getInventory().equals(inventory)) {
//            Boolean isSlotCancelled = isSlotClickCancelled.get(e.getSlot());
//            e.setCancelled(isSlotCancelled != null ? isSlotCancelled : isGlobalClickCancelled);
//            Map<ClickType, Consumer<InventoryClickEvent>> slotEvents = clickEvents.get(e.getSlot());
//            if (slotEvents != null) {
//                Consumer<InventoryClickEvent> event = null;
//                switch (e.getClick()) {
//                    case LEFT:
//                        event = slotEvents.get(ClickType.LEFT);
//                        break;
//                    case RIGHT:
//                        event = slotEvents.get(ClickType.RIGHT);
//                        break;
//                    case MIDDLE:
//                        event = slotEvents.get(ClickType.MIDDLE);
//                        break;
//                    case SHIFT_LEFT:
//                        event = slotEvents.get(ClickType.SHIFT_LEFT);
//                        break;
//                    case SHIFT_RIGHT:
//                        event = slotEvents.get(ClickType.SHIFT_RIGHT);
//                        break;
//                    case WINDOW_BORDER_LEFT:
//                        event = slotEvents.get(ClickType.WINDOW_BORDER_LEFT);
//                        break;
//                    case WINDOW_BORDER_RIGHT:
//                        event = slotEvents.get(ClickType.WINDOW_BORDER_RIGHT);
//                        break;
//                    case DOUBLE_CLICK:
//                        event = slotEvents.get(ClickType.DOUBLE_CLICK);
//                        break;
//                    case NUMBER_KEY:
//                        event = slotEvents.get(ClickType.NUMBER_KEY);
//                        break;
//                    case DROP:
//                        event = slotEvents.get(ClickType.DROP);
//                        break;
//                    case CONTROL_DROP:
//                        event = slotEvents.get(ClickType.CONTROL_DROP);
//                        break;
//                    case CREATIVE:
//                        event = slotEvents.get(ClickType.CREATIVE);
//                        break;
//                    case UNKNOWN:
//                        event = slotEvents.get(ClickType.UNKNOWN);
//                        break;
//                }
//                if (event != null) {
//                    event.accept(e);
//                }
//            }
//        }
//    }
}
