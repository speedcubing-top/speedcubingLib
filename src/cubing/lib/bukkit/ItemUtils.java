package cubing.lib.bukkit;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {

    public static ItemStack setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack glow(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Glow.glow, 1, true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
