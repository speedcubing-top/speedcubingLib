package cubing.bukkit;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public static void fillItem(Inventory inventory, int start, int end, ItemStack stack) {
        for (int i = start; i < end; i++) {
            inventory.setItem(i, stack);
        }
    }
}
