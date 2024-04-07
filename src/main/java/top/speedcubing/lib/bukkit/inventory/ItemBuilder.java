package top.speedcubing.lib.bukkit.inventory;

import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import top.speedcubing.lib.utils.Reflections;

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

    public ItemBuilder addLore(String... lore) {
        List<String> s = meta.getLore();
        if (s == null) {
            meta.setLore(Arrays.asList(lore));
        } else {
            s.addAll(Arrays.asList(lore));
            meta.setLore(s);
        }
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

    @Deprecated
    public ItemBuilder ench(Enchantment[] enchantments, int[] levels) {
        for (int i = 0; i < enchantments.length; i++) {
            meta.addEnchant(enchantments[i], levels[i], true);
        }
        return this;
    }

    public ItemBuilder ench(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder owner(String name) {
        SkullMeta skull = (SkullMeta) meta;
        skull.setOwner(name);
        itemStack.setItemMeta(skull);
        return this;
    }

    /*
    {"textures":{"SKIN":{"url":"http://textures.minecraft.net/texture/..."}}} in base64
    */
    public ItemBuilder skullBase64(String textureBase64) {
        SkullMeta skull = (SkullMeta) meta;
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        gameProfile.getProperties().put("textures", new Property("textures", textureBase64));
        Reflections.setField(skull, "profile", gameProfile);
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
                new JsonParser().parse(new String(Base64.getDecoder().decode(profileValueBase64))).getAsJsonObject()
                .getAsJsonObject("textures")
                        .getAsJsonObject("SKIN")
                        .get("url").getAsString());
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
