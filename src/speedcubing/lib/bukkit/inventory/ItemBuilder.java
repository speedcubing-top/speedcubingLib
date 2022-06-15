package speedcubing.lib.bukkit.inventory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import speedcubing.lib.utils.Reflections;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.UUID;

public class ItemBuilder {

    ItemStack itemStack;
    ItemMeta meta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        meta = itemStack.getItemMeta();
    }

    public ItemBuilder durability(int s) {
        itemStack.setDurability((short) s);
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder unBreak() {
        meta.spigot().setUnbreakable(true);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder hideUnBreak() {
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemBuilder hideAttr() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    public ItemBuilder hideEnch() {
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder hidePotion() {
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        return this;
    }

    public ItemBuilder glow() {
        meta.addEnchant(Glow.glow, 1, true);
        return this;
    }

    public ItemBuilder ench(Enchantment[] enchantments, int[] levels) {
        for (int i = 0; i < enchantments.length; i++) {
            meta.addEnchant(enchantments[i], levels[i], true);
        }
        return this;
    }

    public ItemBuilder owner(String name) {
        SkullMeta skull = (SkullMeta) meta;
        skull.setOwner(name);
        itemStack.setItemMeta(skull);
        return this;
    }

    public ItemBuilder url(String url) {
        SkullMeta skull = (SkullMeta) meta;
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        gameProfile.getProperties().put("textures", new Property("textures", url));
        Reflections.setField(skull, "profile", gameProfile);
        itemStack.setItemMeta(skull);
        return this;
    }

    public ItemBuilder addPotion(PotionEffect effect) {
        PotionMeta potion = (PotionMeta) meta;
        potion.addCustomEffect(effect, true);
        itemStack.setItemMeta(potion);
        return this;
    }

    public ItemStack build() {
        if (meta.hasEnchant(Glow.glow))
            meta.setDisplayName("§r" + (meta.getDisplayName() == null ? "" : meta.getDisplayName()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
