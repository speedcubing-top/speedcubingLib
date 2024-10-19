package top.speedcubing.lib.bukkit.inventory;

import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.speedcubing.lib.utils.ReflectionUtils;

public class ItemBuilder {

    ItemStack itemStack;
    ItemMeta meta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        meta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        meta = itemStack.getItemMeta();
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder durability(int s) {
        itemStack.setDurability((short) s);
        return this;
    }

    public ItemBuilder ench(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder name(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder leatherColor(int rgb) {
        return leatherColor(Color.fromRGB(rgb));
    }

    public ItemBuilder leatherColor(int r, int g, int b) {
        return leatherColor(Color.fromRGB(r, g, b));
    }

    public ItemBuilder leatherColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(color);
        return this;
    }

    public Color getLeatherColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        return leatherArmorMeta.getColor();
    }

    public ItemBuilder owner(String name) {
        SkullMeta skull = (SkullMeta) meta;
        skull.setOwner(name);
        itemStack.setItemMeta(skull);
        return this;
    }

    public ItemBuilder potion(PotionEffectType type, int duration, int amplifier) {
        PotionMeta potion = (PotionMeta) meta;
        potion.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
        itemStack.setItemMeta(potion);
        return this;
    }

    public ItemBuilder unBreak() {
        meta.spigot().setUnbreakable(true);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        lore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        addLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addLore(List<String> lore) {
        List<String> s = meta.getLore();
        if (s == null) {
            lore(lore);
        } else {
            s.addAll(lore);
            meta.setLore(s);
        }
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

    public ItemBuilder hideUnbreak() {
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemBuilder glow() {
        meta.addEnchant(Glow.glow, 1, true);
        return this;
    }

    /*
    {"textures":{"SKIN":{"url":"http://textures.minecraft.net/texture/..."}}} in base64
    */
    public ItemBuilder skullBase64(String textureBase64) {
        SkullMeta skull = (SkullMeta) meta;
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        gameProfile.getProperties().put("textures", new Property("textures", textureBase64));
        ReflectionUtils.setField(skull, "profile", gameProfile);
        itemStack.setItemMeta(skull);
        return this;
    }

    //http://textures.minecraft.net/texture/...
    public ItemBuilder skullFromURL(String url) {
        String json = "{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}";
        return skullBase64(Base64.getEncoder().encodeToString(json.getBytes()));
    }

    public ItemBuilder skullFromProfileValue(String profileValueBase64) {
        return skullFromURL(
                JsonParser.parseString(new String(Base64.getDecoder().decode(profileValueBase64))).getAsJsonObject()
                        .getAsJsonObject("textures")
                        .getAsJsonObject("SKIN")
                        .get("url").getAsString());
    }

    public ItemStack build() {
        if (meta.hasEnchant(Glow.glow))
            meta.setDisplayName("§r" + (meta.getDisplayName() == null ? "" : meta.getDisplayName()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
