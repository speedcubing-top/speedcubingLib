package top.speedcubing.lib.bukkit.inventory;

import java.util.Arrays;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    public static ItemStack setLore(ItemStack itemStack, String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack glow(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Glow.glow, 1, true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack setDisplayName(ItemStack itemStack, String displayName) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
